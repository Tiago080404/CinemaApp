<script>
import HallRoom from "../components/HallRoom.vue";

export default {
  components: { HallRoom },
  methods: {
    goToMovie(movie) {
      console.log(movie.movieId);
      const id = movie.movieId
      this.$router.push({ name: "MoviePage", params: { id } });
    },
  },
};
</script>

<template>
  <div>
    <HallRoom @movie="goToMovie" />
  </div>
</template>
