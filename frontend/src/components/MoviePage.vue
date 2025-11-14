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
      username: "",
      seatsLoaded: false,
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
      //console.log(data);
      await this.newTry(data);
    },

    async newTry(data) {
      let seatObj = {};
      let currentSeatsForRow = {};
      let currentRowNum = 1;
      for (let i = 0; i < data.length; i++) {
        //console.log(data[i]);
        if (data[i].rowNum > currentRowNum) {
          seatObj[currentRowNum] = currentSeatsForRow;
          currentRowNum++;
          currentSeatsForRow = {};
        }
        currentSeatsForRow[data[i].seatNum] = data[i].status;
        //console.log(currentSeatsForRow);
      }
      //console.log(currentRowNum);
      seatObj[currentRowNum] = currentSeatsForRow;
      //console.log(currentSeatsForRow);
      //console.log(seatObj);
      this.seats = seatObj;
      this.seatsLoaded = true;
    },

    async reservateSeats() {
      console.log(this.clickedSeatsArray);
      try {
        const response = await fetch("http://localhost:8080/reservate", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            customerName: this.username,
            reservationTime: new Date(),
            movieId: this.id,
            seats: this.clickedSeatsArray,
          }),
        });
        const data = await response.json();
        console.log("res", data);
        this.clickedSeatsNum = 0;
        if (response.ok) {
          await this.getAllSeatsFromMovie();
          this.showSuccessMessage(data.message);
          this.clickedSeatsArray = [];
        } else {
          console.log(data.message);
          this.showFailMessage(data.message);
          await this.getAllSeatsFromMovie();
        }
      } catch (err) {
        console.log(err);
      }
    },
    showFailMessage(msg) {
      this.$toast.open({
        message: msg,
        type: "error",
        position: "top-right",
        duration: 3000,
      });
    },
    showSuccessMessage(msg) {
      this.$toast.open({
        message: msg,
        type: "success",
        position: "top-right",
        duration: 3000,
      });
    },
    changeSeatStatus(seatIndex, rowIndex) {
      console.log(rowIndex, seatIndex);
      let clickedSeat = {};
      const row = this.seats[rowIndex];
      const currentSeatStatus = row[seatIndex];

      if (currentSeatStatus === "Booked") {
        this.$toast.open({
          message: "Already booked!",
          type: "error",
          position: "top-right",
          duration: 3000,
        });
        return;
      }

      if (currentSeatStatus === null) {
        this.clickedSeatsNum++;
        if (this.clickedSeatsNum > 10) {
          this.$toast.open({
            message: "Only 10 tickets for 1 reservation!",
            type: "error",
            position: "top-right",
            duration: 3000,
          });
          return;
        }
        row[seatIndex] = "onHold";
        console.log(this.seats[rowIndex]);
        clickedSeat["row_num"] = rowIndex;
        clickedSeat["seat_num"] = seatIndex;
        this.clickedSeatsArray.push(clickedSeat);
      } else {
        row[seatIndex] = null;
        this.clickedSeatsNum -= 1;
        const itemToDelete = this.clickedSeatsArray.filter(
          (item) => item.seat_num === seatIndex && item.rom_num === rowIndex
        );
        this.clickedSeatsArray.pop(itemToDelete);
        console.log(this.clickedSeatsArray);
      }
      console.log("aktuelle geklickte", this.clickedSeatsNum);
    },
  },
  async mounted() {
    this.username = localStorage.getItem("username");
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
    <div class="absolute right-5">
      <p>Seats you have clicked: {{ this.clickedSeatsNum }}</p>
      <p>{{ this.username }}</p>
    </div>
    <div
      class="h-6 w-1/4 bg-gray-500 mb-8 shadow-2xl rounded-2xl text-center text-black"
    >
      Leinwand
    </div>
    <div v-if="seatsLoaded === false">
      <img src="../assets/homergif.gif" width="200" alt="" />
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
          seat === 'onHold'
            ? 'bg-blue-600 hover:bg-blue-500'
            : seat == null
            ? 'bg-green-600 hover:bg-green-400'
            : 'bg-red-600 hover:bg-red-400',
        ]"
        @click="changeSeatStatus(seatIndex, rowIndex)"
      >
        {{ seatIndex }}
      </div>
      <p>Reihe{{ rowIndex }}</p>
    </div>
    <button
      @click="reservateSeats"
      class="bg-blue-600 rounded-lg w-24 hover:bg-blue-500 hover:cursor-pointer"
    >
      reservate
    </button>
  </div>
</template>
