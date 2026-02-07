package com.mynextduty.core.service;

import com.mynextduty.core.enums.NotificationType;
import com.mynextduty.core.service.impl.EmailNotification;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

  private final Map<NotificationType, NotificationChannel> channelMap;

  public NotificationFactory(List<NotificationChannel> channels) {
    channelMap = new EnumMap<>(NotificationType.class);
    for (NotificationChannel channel : channels) {
      if (channel instanceof EmailNotification) {
        channelMap.put(NotificationType.EMAIL, channel);
      }
    }
  }

  public NotificationChannel getChannel(NotificationType type) {
    NotificationChannel channel = channelMap.get(type);
    if (channel == null) {
      throw new IllegalArgumentException("Unsupported notification type: " + type);
    }
    return channel;
  }
}
