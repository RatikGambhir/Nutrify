import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {
  state: () => ({ user: {}, session: {}, error: null }),
  getters: {
    getUser: (state) => state.user,
    getSession: (state) => state.session,
    getError: (state) => state.error,
  },
  actions: {
    addUserSession(user: object, session: object) {
      this.user = user;
      this.session = session;
    },
  },
});
