<script>
export default {
  props: {
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {};
  },
  methods: {
    async getMovieById() {
      try {
        const response = await fetch("http://localhost:8080/movie", {
          method: "GET",
          headers: { "Content-Type": "application/json" },
        });
        const data = await response.json();
      } catch (err) {
        console.log(err);
      }
    },
  },
  async mounted() {
    await this.getMovieById()
  },
};
</script>
<template>
  <div>
    <h1>hello{{ id }}</h1>
  </div>
</template>
