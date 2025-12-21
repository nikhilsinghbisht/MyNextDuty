package com.mynextduty.core.dto;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SuccessResponseDto<T> implements ResponseDto<T> {
  private String message = "Request processed successfully.";
  private Integer status = 200;
  private T data;

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public int getStatus() {
    return this.status;
  }

  @Override
  public T getData() {
    return this.data;
  }

  @Override
  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public void setData(T data) {
    this.data = data;
  }

  SuccessResponseDto(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  SuccessResponseDto(int status, String message) {
    this.status = status;
    this.message = message;
  }

  SuccessResponseDto(String message, T data) {
    this.message = message;
    this.data = data;
  }

  SuccessResponseDto(int status, T data) {
    this.status = status;
    this.data = data;
  }
}
