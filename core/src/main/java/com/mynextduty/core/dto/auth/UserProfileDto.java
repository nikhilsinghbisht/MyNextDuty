package com.mynextduty.core.dto.auth;

import com.mynextduty.core.enums.LifeStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserProfileDto {
  private Long id;
  private String email;
  private String firstName;
  private String lastName;
  private String currentOccupation;
  private LocalDate dateOfBirth;
  private LifeStage lifeStage;
  private Double monthlyIncome;
  private String educationLevel;
  private boolean isVerified;
}