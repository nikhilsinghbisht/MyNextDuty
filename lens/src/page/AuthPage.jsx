import { useState } from "react";
import Button from "../components/common/Button";
import InputField from "../components/common/InputField";
import formConfig from "../config/formConfig";
import { useAuth } from "../hooks/useAuth";
import Loader from "../components/common/Loader";

export const AuthPage = () => {
  const [mode, setMode] = useState("login");
  const [values, setValues] = useState({});
  const [errors, setErrors] = useState({});

  const { login, signup, loading, error } = useAuth();

  const fields = formConfig[mode].fields;

  const handleChange = (name, value) => {
    setValues((prev) => ({ ...prev, [name]: value }));
    setErrors((prev) => ({ ...prev, [name]: "" }));
  };

  const validateForm = () => {
    const newErrors = {};

    fields.forEach((field) => {
      const value = values[field.name] || "";

      if (field.required && !value) {
        newErrors[field.name] = `${field.label} is required`;
        return;
      }

      if (field.validation) {
        const result = field.validation(value, values);
        if (result !== true) {
          newErrors[field.name] = result;
        }
      }
    });

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      if (mode === "login") {
        await login(values);
      } else {
        await signup(values);
      }
      // redirect handled elsewhere or here
    } catch {
      // error already handled in hook
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: "auto" }}>
      <h2>{mode === "login" ? "Login" : "Sign Up"}</h2>

      <form onSubmit={handleSubmit}>
        {fields.map((field) => (
          <InputField
            key={field.name}
            label={field.label}
            type={field.type}
            placeholder={field.placeholder}
            value={values[field.name] || ""}
            error={errors[field.name]}
            onChange={(e) => handleChange(field.name, e.target.value)}
          />
        ))}

        <Button type="submit" disabled={loading} style={{ width: "100%", marginTop: 16 }}>
          {loading ? <Loader size="sm" /> : mode === "login" ? "Login" : "Sign Up"}
        </Button>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <div style={{ marginTop: 16, textAlign: "center" }}>
        {mode === "login" ? (
          <button onClick={() => setMode("signup")}>Create account</button>
        ) : (
          <button onClick={() => setMode("login")}>Back to login</button>
        )}
      </div>
    </div>
  );
};

export default AuthPage;
