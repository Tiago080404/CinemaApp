<script>
import HallRoom from "../components/HallRoom.vue";
import LoginPage from "./LoginPage.vue";

export default {
  data() {
    return {
      loggedIn: false,
      user: "",
    };
  },
  components: { HallRoom, LoginPage },
  methods: {
    goToMovie(movie) {
      const id = movie.movieId;
      this.$router.push({ name: "MoviePage", params: { id } });
    },
    logginIn(username) {
      localStorage.setItem("username", username);
      this.user = username;
      this.loggedIn = true;
    },
  },
  mounted() {
    if (localStorage.getItem("username")) {
      this.loggedIn = true;
    }
  },
};
</script>

<template>
  <div>
    <HallRoom v-if="loggedIn" @movie="goToMovie" />
    <div v-else>
      <LoginPage @log-in="logginIn"></LoginPage>
    </div>
  </div>
</template>
