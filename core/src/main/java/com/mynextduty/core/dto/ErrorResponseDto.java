package com.mynextduty.core.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
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

  ErrorResponseDto(int status, String message) {
    this.status = status;
    this.message = message;
    this.timestamp = new Date().getTime();
  }

  ErrorResponseDto(int status, String message, String error) {
    this.status = status;
    this.message = message;
    this.timestamp = new Date().getTime();
    this.error = error;
  }

  ErrorResponseDto(String message, T data, String error) {
    this.message = message;
    this.data = data;
    this.timestamp = new Date().getTime();
    this.error = error;
  }

  ErrorResponseDto(int status, T data, String error) {
    this.status = status;
    this.data = data;
    this.timestamp = new Date().getTime();
    this.error = error;
  }
}
