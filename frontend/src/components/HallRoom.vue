<script>
export default {
  data() {
    return {
      movies: [],
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
    },
    showMovie(movie) {
      this.$emit("movie", movie);
    },
  },
  async mounted() {
    await this.getMovies();
  },
};
</script>
<template>
  <div>
    <div class="flex justify-center mt-10">
      <ul class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
        <li v-for="(movie, index) in movies" :key="index">
          <div
            class="flex justify-center items-center flex-col p-4 shadow-lg rounded-xl gap-2 text-black hover:cursor-pointer duration-300 hover:scale-105 hover:shadow-2xl transform transition bg-white"
            @click="showMovie(movie)"
          >
            <h2 class="text-lg font-bold">{{ movie.titel }}</h2>
            <p class="text-gray-700">{{ movie.movieDate }}</p>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<style scoped></style>
