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
      console.log(data);
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
        console.log(
          "row",
          data[i].rowNum,
          "seat",
          data[i].seatNum,
          data[i].status
        );
        if (data[i].rowNum > currentRowNum) {
          console.log("changed", seatAndStatus, "seatsarray", seatsArray);
          //console.log("roooownum",data[i].rowNum)
          seatObj[currentRowNum] = seatsArray;
          seatsArray = [];

          currentRowNum++;
          //seatAndStatus = {};
        }
        console.log(
          data[i].seatNum,
          "und status",
          data[i].status,
          seatAndStatus
        );
        seatAndStatus[data[i].seatNum] = data[i].status;
        console.log("status object", seatAndStatus);
        seatsArray.push(seatAndStatus);
        console.log("sitze", seatsArray);
      }
      seatObj[currentRowNum] = seatsArray;
      console.log(seatObj);
      this.seats = seatObj;

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
          seat[seatIndex + 1] == null
            ? 'bg-green-600 hover:bg-green-400'
            : 'bg-red-600 hover:bg-red-400',
        ]"
      >
        {{ seatIndex + 1 }}
      </div>
      <p>Reihe{{ rowIndex }}</p>
    </div>
  </div>
</template>
