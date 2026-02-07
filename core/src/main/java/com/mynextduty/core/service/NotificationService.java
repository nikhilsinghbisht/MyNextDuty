package com.mynextduty.core.service;

import com.mynextduty.core.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationFactory notificationFactory;

  public void send(NotificationRequest request) {
    NotificationChannel channel = notificationFactory.getChannel(request.getType());
    channel.send(request);
  }
}
