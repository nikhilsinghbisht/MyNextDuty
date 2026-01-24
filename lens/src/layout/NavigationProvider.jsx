import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { setNavigator } from "../service/navigation.service";

const NavigationProvider = ({ children }) => {
  const navigate = useNavigate();

  useEffect(() => {
    setNavigator(navigate);
  }, [navigate]);

  return children;
};

export default NavigationProvider;
