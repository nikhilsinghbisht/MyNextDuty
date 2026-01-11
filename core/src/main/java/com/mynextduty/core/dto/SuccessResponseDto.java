package com.mynextduty.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
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

  public SuccessResponseDto(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public SuccessResponseDto(String message, T data) {
    this.message = message;
    this.data = data;
  }

  public SuccessResponseDto(int status, T data) {
    this.status = status;
    this.data = data;
  }

  public SuccessResponseDto(T data) {
    this.data = data;
  }
}
