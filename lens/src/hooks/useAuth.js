import { useDispatch, useSelector } from "react-redux";
import { authService } from "../service/auth.service";
import {
  authLoginRequest,
  authLoginSuccess,
  authLoginFailure,
  authLogout,
} from "../redux/actions/auth.actions";

export const useAuth = () => {
  const dispatch = useDispatch();

  const { loading, error, user, isAuthenticated } = useSelector((state) => state.auth);

  const login = async (values) => {
    dispatch(authLoginRequest());

    try {
      const response = await authService.login(values);

      dispatch(
        authLoginSuccess({
          user: response.data.user,
          token: response.data.accessToken,
        })
      );

      return response.data;
    } catch (err) {
      dispatch(authLoginFailure(err.response?.data?.message || "Login failed"));
      throw err;
    }
  };

  const signup = async (values) => {
    dispatch(authLoginRequest());

    try {
      const response = await authService.signup(values);

      dispatch(
        authLoginSuccess({
          user: response.data.user,
          token: response.data.accessToken,
        })
      );

      return response.data;
    } catch (err) {
      dispatch(authLoginFailure(err.response?.data?.message || "Signup failed"));
      throw err;
    }
  };

  const logout = async () => {
    try {
      await authService.logout();
    } finally {
      dispatch(authLogout());
    }
  };

  return {
    login,
    signup,
    logout,

    // derived state
    loading,
    error,
    user,
    isAuthenticated,
  };
};
