import { useState } from "react";
import Button from "../components/common/Button";
import InputField from "../components/common/InputField";
import formConfig from "../config/formConfig";
import { useAuth } from "../hooks/useAuth";
import Loader from "../components/common/Loader";
import logo from "../Image/mynextdutylogo.svg";

import "./AuthPage.css";

export const AuthPage = () => {
  const [mode, setMode] = useState("login");
  const [values, setValues] = useState({ email: "", password: "" });
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
        if (result !== true) newErrors[field.name] = result;
      }
    });
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;
    mode === "login" ? await login(values) : await signup(values);
  };

  return (
    <div className="auth-page">

      {/* ===== OUTSIDE CARD ===== */}
      <div className="auth-header">
        <div className="auth-logo-text">
          <img src={ logo } alt="MyNextDuty Logo" />
        </div>
        
        <p className="auth-subtitle">
            Find clarity in your <span>next step</span>
        </p>

      </div>

      {/* ===== FORM CARD ===== */}
      <div className="auth-card">
        <h2 className="auth-heading">
          {mode === "login" ? "Welcome back" : "Create your account"}
        </h2>
        
        <form onSubmit={handleSubmit} className="auth-form">

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

          {mode === "login" && (
            <div className="auth-forgot">Forgot password?</div>
          )}

          <Button type="submit" disabled={loading}>
            {loading ? <Loader size="sm" /> : mode === "login" ? "Log In" : "Create Account"}
          </Button>

          {error && <p className="auth-error">{error}</p>}

          <div className="auth-divider" />

          <div className="auth-switch">
            {mode === "login" ? (
              <button type="button" onClick={() => setMode("signup")}>
                New here? Create an account
              </button>
            ) : (
              <button type="button" onClick={() => setMode("login")}>
                Back to login
              </button>
            )}
          </div>

        </form>
      </div>
    </div>
  );
};

export default AuthPage;
