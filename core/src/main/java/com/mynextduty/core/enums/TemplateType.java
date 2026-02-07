package com.mynextduty.core.enums;

import lombok.Getter;

@Getter
public enum TemplateType {
  EMAIL_VERIFICATION("email/email-verification"),
  PASSWORD_RESET(""),
  WELCOME("");

  private final String templatePath;

  TemplateType(String templatePath) {
    this.templatePath = templatePath;
  }
}
