<script>
import HallRoom from "./components/HallRoom.vue";
export default {
  components: {
    HallRoom,
  },
  data() {
    return {};
  },
  methods: {},
};
</script>

<template>
  <div
    class="min-h-screen min-w-screen bg-linear-to-br from-gray-900 via-gray-800 to-black text-white overflow-hidden"
  >
    <RouterView></RouterView>
  </div>
</template>
<style scoped></style>
