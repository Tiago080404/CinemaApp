<script>
export default {
  data() {
    return {};
  },
  methods: {
    async getMovies() {
      const response = await fetch("http://localhost:8080/movie", {
        method: "GET",
        "Content-Type": "application/json",
      });
      const data = await response.json();
      console.log(data);
    },
  },
  async mounted() {
    await this.getMovies();
  },
};
</script>
<template>
  <div></div>
</template>
<style scoped></style>
