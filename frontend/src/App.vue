<script>
import HallRoom from "./components/HallRoom.vue";
export default {
  components: {
    HallRoom,
  },
  data() {
    return {};
  },
  emits: ["movie"],
  methods: {
    goToMovie(movie) {
      console.log(movie);
    },
  },
};
</script>

<template>
  <div
    class="min-h-screen min-w-screen bg-linear-to-br from-gray-900 via-gray-800 to-black text-white overflow-hidden"
  >
    <HallRoom @movie="goToMovie"></HallRoom>
  </div>
</template>
<style scoped></style>
