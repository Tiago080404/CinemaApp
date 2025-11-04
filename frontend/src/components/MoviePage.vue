<script>
export default {
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      seats: {},
      clickedSeatsNum: 0,
      clickedSeatsArray: [],
    };
  },
  methods: {
    async getMovieById() {
      try {
        const response = await fetch(`http://localhost:8080/movie/${this.id}`, {
          method: "GET",
          headers: { "Content-Type": "application/json" },
        });
        const data = await response.json();
        console.log(data);
      } catch (err) {
        console.log(err);
      }
    },
    async getAllSeatsFromMovie() {
      const response = await fetch(
        `http://localhost:8080/movie/seats/${this.id}`,
        {
          method: "GET",
          headers: { "Content-Type": "application/json" },
        }
      );
      const data = await response.json();
      await this.changeTheData(data);
    },
    async changeTheData(data) {
      /*
    {1:
        [{seatnum:booked}
           ]
             }
        */
      let currentRowNum = 1;
      let seatObj = {};
      let seatsArray = [];
      for (let i = 0; i < data.length; i++) {
        let seatAndStatus = {};

        if (data[i].rowNum > currentRowNum) {
          seatObj[currentRowNum] = seatsArray;
          seatsArray = [];

          currentRowNum++;
        }

        seatAndStatus[data[i].seatNum] = data[i].status;
        seatsArray.push(seatAndStatus);
      }
      seatObj[currentRowNum] = seatsArray;
      this.seats = seatObj;
      console.log(this.seats)
    },
    async reservateSeats() {
      console.log(this.clickedSeatsArray);
    },
    changeSeatStatus(seatIndex, rowIndex) {
      let clickedSeats = {};
      const row = this.seats[rowIndex];
      const seat = row[seatIndex];
      const seatNum = Object.keys(seat)[0];
      const currentStatus = seat[seatNum];
      console.log(
        `Reihe ${rowIndex}, SitzNummer ${seatNum}, Status:`,
        currentStatus
      );
      if (currentStatus === "Booked") {
        alert("Cant book a seat that is already booked");
        return;
      }
      if (currentStatus === null) {
        if (this.clickedSeatsNum === 10) {
          alert("Can only reservate 10 seats");
          return;
        }
        this.clickedSeatsNum += 1;
        seat[seatNum] = "onHold";
        clickedSeats["row_num"] = rowIndex;
        clickedSeats["seat_num"] = seatNum;
        console.log(clickedSeats);
        this.clickedSeatsArray.push(clickedSeats);
      } else {
        this.clickedSeatsNum -= 1;
        seat[seatNum] = null;

        let deleteItem = this.clickedSeatsArray.filter(
          (item) => item.row_num === rowIndex && item.seat_num === seatNum
        );
        this.clickedSeatsArray.pop(deleteItem);
        console.log(clickedSeats);
      }
    },
  },
  async mounted() {
    await this.getMovieById();
    await this.getAllSeatsFromMovie();
  },
};
</script>
<template>
  <!-- leinwand -->
  <!-- seats -->
  <!-- reihe -->

  <div class="flex flex-col items-center p-8 min-h-screen">
    <div
      class="h-6 w-1/4 bg-gray-500 mb-8 shadow-2xl rounded-2xl text-center text-black"
    >
      Leinwand
    </div>
    <div
      v-for="(row, rowIndex) in seats"
      :key="rowIndex"
      class="flex justify-center space-x-3 space-y-3"
    >
      <div
        v-for="(seat, seatIndex) in row"
        :key="seatIndex"
        :class="[
          'w-10 h-10 flex flex-col justify-center items-center rounded-lg text-xs font-bold cursor-pointer transition-all',
          seat[seatIndex + 1] === 'onHold'
            ? 'bg-blue-600 hover:bg-blue-500'
            : seat[seatIndex + 1] == null
            ? 'bg-green-600 hover:bg-green-400'
            : 'bg-red-600 hover:bg-red-400',
        ]"
        @click="changeSeatStatus(seatIndex, rowIndex)"
      >
        {{ seatIndex + 1 }}
      </div>
      <p>Reihe{{ rowIndex }}</p>
    </div>
    <button @click="reservateSeats">reservate</button>
  </div>
</template>
