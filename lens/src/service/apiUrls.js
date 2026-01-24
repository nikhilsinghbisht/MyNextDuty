export const CORE_BASE_URL = import.meta.env.VITE_CORE_BASE_URL;

export const API_URLS = {
  AUTH: {
    LOGIN: "/auth/login",
    REFRESH: "/auth/refresh",
    LOGOUT: "/auth/logout",
  },
  USER: {
    PROFILE: "/user/profile",
  },
};
