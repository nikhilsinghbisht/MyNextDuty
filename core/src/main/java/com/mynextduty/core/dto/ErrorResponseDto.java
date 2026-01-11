package com.mynextduty.core.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ErrorResponseDto<T> implements ResponseDto<T> {
  private Long timestamp;
  private String error;
  private String message;
  private Integer status;
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

  public ErrorResponseDto(int status, String message) {
    this.status = status;
    this.message = message;
    this.timestamp = new Date().getTime();
  }

  public ErrorResponseDto(int status, String message, String error) {
    this.status = status;
    this.message = message;
    this.timestamp = new Date().getTime();
    this.error = error;
  }

  public ErrorResponseDto(String message, T data, String error) {
    this.message = message;
    this.data = data;
    this.timestamp = new Date().getTime();
    this.error = error;
  }

  public ErrorResponseDto(int status, T data, String error) {
    this.status = status;
    this.data = data;
    this.timestamp = new Date().getTime();
    this.error = error;
  }
}
