package com.mynextduty.core.dto;

import com.mynextduty.core.enums.NotificationType;
import com.mynextduty.core.enums.TemplateType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class NotificationRequest {
  private String recipient;
  private NotificationType type;
  private TemplateType templateType;
  private Map<String, Object> data;
}
