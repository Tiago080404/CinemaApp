import { createRouter, createWebHistory } from "vue-router";
import MoviePage from "../components/MoviePage.vue";
import HomePage from "../components/HomePage.vue";

const routes = [
  { path: "/", component: HomePage },
  { path: "/movie/:id", name: "MoviePage", component: MoviePage, props: true },
];

export const router = createRouter({
  history: createWebHistory(),
  routes,
});
