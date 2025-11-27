<script>
export default {
  data() {
    return {
      movies: [],
      filteredMovies: [],
      newMovieInsert: false,
      movieTitel: "",
      movieDate: "",
      movieHall: 0,
      movieTime: "",
      titelFilter: "",
      dateFilter: "",
      orderUp: false,
    };
  },
  methods: {
    async getMovies() {
      const response = await fetch("http://localhost:8080/movie", {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      });
      const data = await response.json();
      console.log(data);
      this.movies = data;
      this.filteredMovies = data;
    },
    showMovie(movie) {
      this.$emit("movie", movie);
    },
    async insertNewMovie() {
      const convertedDate = this.movieDate + "T" + this.movieTime + ":00";
      try {
        const response = await fetch("http://localhost:8080/movie/insert", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            titel: this.movieTitel,
            movieDate: convertedDate,
            hall: this.movieHall,
          }),
        });
        await response.json();
        if (response.ok) {
          this.$toast.open({
            message: "New movie added!",
            type: "success",
            position: "top-right",
            duration: 3000,
          });
          console.log("New movie added");
        }
      } catch (err) {
        console.log(err);
      }
    },
    filterByTitel() {
      console.log(this.titelFilter);
      this.filteredMovies = this.movies.filter((movie) =>
        movie.titel.toLowerCase().includes(this.titelFilter.toLowerCase())
      );
    },
    filterByDate() {
      console.log("date", this.dateFilter, this.movies[1].showDate);
      if (this.dateFilter === "") {
        this.filteredMovies = this.movies;
        console.log(this.filteredMovies);
        return;
      }
      this.filteredMovies = [];
      for (let i = 0; i < this.movies.length; i++) {
        for (const date of this.movies[i].showDate) {
          if (date === this.dateFilter) {
            this.filteredMovies.push(this.movies[i]);
          }
        }
      }
    },
    changeOrder(orderOption) {
      if (orderOption === "up") {
        console.log(this.movies.sort((a, b) => a.popularity - b.popularity));
        this.filteredMovies = this.movies.sort(
          (a, b) => a.popularity - b.popularity
        );
        this.orderUp = !this.orderUp;
      } else {
        console.log(this.movies.sort((a, b) => b.popularity - a.popularity));
        this.filteredMovies = this.movies.sort(
          (a, b) => b.popularity - a.popularity
        );
        this.orderUp = !this.orderUp;
      }
    },
  },
  async mounted() {
    await this.getMovies();
  },
};
</script>
<template>
  <div class="relative">
    <div
      v-if="newMovieInsert"
      class="fixed inset-0 flex justify-center items-center bg-linear-to-br from-gray-900 via-gray-800 to-black z-50"
    >
      <div class="bg-white text-black p-6 rounded-xl shadow-lg w-96">
        <button
          class="relative bottom-6 bg-red-500 rounded-2xl w-6 right-5.5 hover:bg-red-600"
          @click="newMovieInsert = false"
        >
          X
        </button>
        <h2 class="text-xl font-bold mb-4">Neuen Film hinzufügen</h2>
        <div>
          <input
            type="text"
            placeholder="moviename"
            class="border w-full p-2 mb-3 rounded-2xl"
            v-model="movieTitel"
          />
          <input
            type="date"
            placeholder="datetime-local"
            class="border w-full p-2 mb-3 rounded-2xl"
            v-model="movieDate"
          />
          <input
            type="number"
            placeholder="hall"
            class="border w-full p-2 mb-3 rounded-2xl"
            v-model="movieHall"
          />
          <input type="time" v-model="movieTime" />
          <button
            class="border-green-500 w-full rounded-2xl bg-green-500 hover:bg-green-600"
            @click="insertNewMovie"
          >
            Submit
          </button>
        </div>
      </div>
    </div>
  </div>
  <div class="flex justify-center mt-10">
    <div>
      <button
        @click="newMovieInsert = true"
        class="absolute top-0 right-1 bg-green-600 rounded-lg w-7 hover:bg-green-700"
      >
        +
      </button>
      <input
        v-model="this.titelFilter"
        type="text"
        class="absolute top-0 right-20 bg-white text-black rounded-lg"
        @change="filterByTitel"
      />
      <input
        v-model="this.dateFilter"
        type="date"
        class="absolute top-0 right-80"
        @change="filterByDate"
      />
      <span
        @click="this.changeOrder('up')"
        :class="[
          'absolute top-0 right-190 hover:cursor-pointer',
          orderUp === true ? 'text-blue-600' : 'text-black',
        ]"
      >
        ▲
      </span>
      <button
        @click="this.changeOrder('down')"
        :class="[
          'absolute top-0 right-185 hover:cursor-pointer',
          orderUp === false ? 'text-blue-600' : 'text-black',
        ]"
      >
        ▼
      </button>
    </div>
    <ul class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
      <li v-for="(movie, index) in filteredMovies" :key="index">
        <div
          class="flex justify-center items-center flex-col p-3 w-150 shadow-lg rounded-xl gap-2 text-black hover:cursor-pointer duration-300 hover:scale-105 hover:shadow-2xl transform transition bg-white"
          @click="showMovie(movie)"
        >
          <h2 class="text-lg font-bold">{{ movie.titel }}</h2>
          <!--  -->
          <img
            :src="'http://image.tmdb.org/t/p/w500/' + movie.image"
            class="rounded-xl h-200"
          />
          <p class="text-gray-700">{{ movie.movieDate }}</p>
        </div>
      </li>
    </ul>
  </div>
</template>
<style scoped></style>
