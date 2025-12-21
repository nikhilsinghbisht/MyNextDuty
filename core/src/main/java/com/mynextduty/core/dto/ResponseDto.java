package com.mynextduty.core.dto;

public interface ResponseDto<T> {
  String getMessage();

  int getStatus();

  T getData();

  void setMessage(String message);

  void setStatus(int status);

  void setData(T data);
}
