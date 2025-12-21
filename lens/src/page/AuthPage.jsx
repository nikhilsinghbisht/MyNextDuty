import Button  from "../components/common/Buttton";
import form from "../config";

export const AuthPage = () => {
  const isLoggedIn = localStorage.getItem("isLoggedIn");
  const handleChange = () => {};
  return (
    <>
      {form.filter(() => {})}
      <Button text={"Login"} onClick={handleChange} />
    </>
  );
};
