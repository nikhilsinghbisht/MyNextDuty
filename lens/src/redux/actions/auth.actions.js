// redux/actions/auth.actions.js

export const AUTH_LOGIN_REQUEST = "AUTH_LOGIN_REQUEST";
export const AUTH_LOGIN_SUCCESS = "AUTH_LOGIN_SUCCESS";
export const AUTH_LOGIN_FAILURE = "AUTH_LOGIN_FAILURE";

export const AUTH_LOGOUT = "AUTH_LOGOUT";

export const authLoginRequest = () => ({
  type: AUTH_LOGIN_REQUEST,
});

export const authLoginSuccess = (payload) => ({
  type: AUTH_LOGIN_SUCCESS,
  payload, // { user, token }
});

export const authLoginFailure = (error) => ({
  type: AUTH_LOGIN_FAILURE,
  payload: error,
});

export const authLogout = () => ({
  type: AUTH_LOGOUT,
});
