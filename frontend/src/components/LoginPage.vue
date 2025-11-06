<script>
export default {
  data() {
    return {
      username: "",
    };
  },
  methods: {
    sendLogInData() {
      console.log("SAFSA");
      console.log(this.username);
      this.$emit("log-in", this.username);
    },
  },
};
</script>
<template>
  <div class="min-h-screen max-w-screen flex justify-center items-center">
    <div class="p-4 bg-white rounded-2xl h-50 flex justify-center items-center">
      <form action="" class="flex flex-col gap-2">
        <input
          type="text"
          placeholder="username"
          class="bg-linear-to-br from-gray-900 via-gray-800 to-black text-white p-3 rounded-lg"
          v-model="this.username"
        />
        <button
          class="bg-linear-to-br from-gray-900 via-gray-800 to-black rounded-lg w-20"
          @click="sendLogInData"
        >
          Submit
        </button>
      </form>
    </div>
  </div>
</template>
