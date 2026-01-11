package com.mynextduty.core.service;

import com.mynextduty.core.dto.DutyRecommendationDto;
import com.mynextduty.core.entity.User;
import java.util.List;

public interface RecommendationService {

  /** Get personalized duty recommendations for a user */
  List<DutyRecommendationDto> getPersonalizedRecommendations(Long userId);

  /** Get recommendations based on life stage */
  List<DutyRecommendationDto> getRecommendationsByLifeStage(Long userId);

  /** Get recommendations based on user interests */
  List<DutyRecommendationDto> getRecommendationsByInterests(Long userId);

  /** Get critical/high priority recommendations */
  List<DutyRecommendationDto> getCriticalRecommendations(Long userId);

  /** Update user's life stage based on profile changes */
  void updateUserLifeStage(User user);
}
