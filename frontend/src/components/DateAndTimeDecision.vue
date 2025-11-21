<script>
export default {
  emits: ["dateTimeObject"],
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      movieShowDateAndTime: {},
      movieHall: 0,
    };
  },
  methods: {
    async getAllShowsForMovie() {
      try {
        const response = await fetch(
          `http://localhost:8080/movie/all/${this.id}`,
          {
            method: "GET",
            headers: { "Content-Type": "application/json" },
          }
        );
        const data = await response.json();

        console.log(data);
        this.movieHall = data.showTimes[0].hall.hallId;
        for (const showTime of data.showTimes) {
            console.log("dasdafsd",showTime.show_date)
          this.movieShowDateAndTime[showTime.show_date] = [];
          this.movieShowDateAndTime[showTime.show_date].push(
            showTime.show_time
          );
          this.movieShowDateAndTime[showTime.show_date].push("12:00:00");
        }
      } catch (err) {
        console.log("error:", err);
      }
    },
    dateAndTimeClicked(date, time) {
      console.log(date, time);
      const dateTimeHallObject = { date, time,movieHall:this.movieHall };
      console.log(dateTimeHallObject);
      this.$emit("selectedDateTime", dateTimeHallObject);
    },
  },
  async mounted() {
    await this.getAllShowsForMovie();
  },
};
</script>
<template>
  <div class="flex flex-col items-center p-8 min-h-screen">
    <ul>
      <li v-for="(date, index) in this.movieShowDateAndTime">
        <div class="flex flex-row gap-2">
          <div class="bg-black rounded-xl">
            {{ index }}
          </div>
          <div
            class="bg-black rounded-xl cursor-pointer"
            v-for="(specific, i) in date"
            :key="i"
            @click="dateAndTimeClicked(index, specific)"
          >
            {{ specific }}
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>
