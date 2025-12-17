import { defineNuxtConfig } from "nuxt/config";

export default defineNuxtConfig({
  srcDir: "app",
  devtools: { enabled: true },

  modules: [
    "@nuxt/ui-pro",
    "@nuxt/eslint",
    "@nuxt/image",
    "@nuxt/scripts",
    "@nuxt/test-utils",
    "@pinia/nuxt",
  ],

  css: ["~/assets/css/main.css"],

  compatibilityDate: "2025-07-16",
});
