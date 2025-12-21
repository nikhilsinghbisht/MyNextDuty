import React from "react";

const InputField = ({ value, placeholder, type = "text", label }) => {
  return (
    <div className="input-field">
      {label && <label htmlFor={placeholder}>{label}</label>}
      <input type={type} id={placeholder} value={(e) => setValue(e)} placeholder={placeholder} />
    </div>
  );
};

export default InputField;
