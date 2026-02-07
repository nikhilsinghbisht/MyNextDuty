package com.mynextduty.core.service;

import com.mynextduty.core.dto.NotificationRequest;

public interface NotificationChannel {
  void send(NotificationRequest request);
}
