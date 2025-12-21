import InputField from "./InputField";
import { use, useState } from "react";

export const Loader = () => {
  const { value, setValue } = useState();
  return (
    <>
      <h1>Loading...</h1>
      <InputField type="text" value={setValue} placeholder={"username"} label={"username"} />
      {value}
    </>
  );
};
