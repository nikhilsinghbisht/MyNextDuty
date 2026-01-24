import api from "../service/axiosInstance";
import { API_URLS } from "../service/apiUrls";

export const authService = {
  login(payload) {
    return api.post("/auth/login", payload);
  },

  signup(payload) {
    return api.post("/auth/signup", payload);
  },

  logout() {
    return api.post("/auth/logout");
  },
};
