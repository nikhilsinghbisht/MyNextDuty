import React from "react";
import {
  wrapperStyle,
  labelStyle,
  inputBaseStyle,
  variants,
  sizes,
  errorStyle,
  helperTextStyle,
  errorTextStyle,
} from "./input.styles";
import { INPUT_VARIANTS, INPUT_SIZES } from "./input.constants";

const InputField = React.forwardRef(
  (
    {
      label,
      helperText,
      error,
      variant = INPUT_VARIANTS.DEFAULT,
      size = INPUT_SIZES.MD,
      leftIcon,
      rightIcon,
      style,
      inputStyle,
      id,
      ...props
    },
    ref
  ) => {
    const inputId = id || React.useId();
    const describedBy = error || helperText ? `${inputId}-desc` : undefined;

    return (
      <div style={wrapperStyle}>
        {label && (
          <label htmlFor={inputId} style={labelStyle}>
            {label}
          </label>
        )}

        <div style={{ position: "relative", display: "flex" }}>
          {leftIcon && (
            <span
              style={{ position: "absolute", left: 8, top: "50%", transform: "translateY(-50%)" }}
            >
              {leftIcon}
            </span>
          )}

          <input
            ref={ref}
            id={inputId}
            aria-invalid={!!error}
            aria-describedby={describedBy}
            style={{
              ...inputBaseStyle,
              ...variants[variant],
              ...sizes[size],
              ...(error ? errorStyle : {}),
              paddingLeft: leftIcon ? "32px" : undefined,
              paddingRight: rightIcon ? "32px" : undefined,
              ...inputStyle,
            }}
            {...props}
          />

          {rightIcon && (
            <span
              style={{ position: "absolute", right: 8, top: "50%", transform: "translateY(-50%)" }}
            >
              {rightIcon}
            </span>
          )}
        </div>

        {(error || helperText) && (
          <span id={describedBy} style={error ? errorTextStyle : helperTextStyle}>
            {error || helperText}
          </span>
        )}
      </div>
    );
  }
);

InputField.displayName = "InputField";

export default InputField;
