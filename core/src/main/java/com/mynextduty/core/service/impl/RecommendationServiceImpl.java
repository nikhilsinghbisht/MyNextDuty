package com.mynextduty.core.service.impl;

import com.mynextduty.core.dto.DutyRecommendationDto;
import com.mynextduty.core.entity.Duties;
import com.mynextduty.core.entity.User;
import com.mynextduty.core.entity.UserDutyProgress;
import com.mynextduty.core.enums.LifeStage;
import com.mynextduty.core.enums.Priority;
import com.mynextduty.core.enums.ProgressStatus;
import com.mynextduty.core.repository.DutiesRepository;
import com.mynextduty.core.repository.UserDutyProgressRepository;
import com.mynextduty.core.repository.UserRepository;
import com.mynextduty.core.service.RecommendationService;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.mynextduty.core.utils.Helper.getLifeStage;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

  private final UserRepository userRepository;
  private final DutiesRepository dutiesRepository;
  private final UserDutyProgressRepository userDutyProgressRepository;

  @Override
  public List<DutyRecommendationDto> getPersonalizedRecommendations(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    List<Duties> allDuties = new ArrayList<>();
    if (user.getLifeStage() != null) {
      allDuties.addAll(dutiesRepository.findByTargetLifeStageAndIsActiveTrue(user.getLifeStage()));
    }
    if (user.getDateOfBirth() != null) {
      int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
      allDuties.addAll(dutiesRepository.findByAgeRange(age));
    }
    allDuties.addAll(dutiesRepository.findByUserInterests(userId));
    Set<Duties> uniqueDuties = new HashSet<>(allDuties);
    List<UserDutyProgress> userProgress = userDutyProgressRepository.findByUserId(userId);
    Map<Long, UserDutyProgress> progressMap =
        userProgress.stream().collect(Collectors.toMap(p -> p.getDuties().getId(), p -> p));
    return uniqueDuties.stream()
        .map(duty -> convertToDutyRecommendationDto(duty, user, progressMap.get(duty.getId())))
        .sorted(
            (a, b) -> {
              // Sort by priority first, then by match score
              int priorityCompare =
                  getPriorityWeight(a.getPriority()) - getPriorityWeight(b.getPriority());
              if (priorityCompare != 0) return priorityCompare;
              return b.getMatchScore() - a.getMatchScore();
            })
        .limit(20) // Limit to top 20 recommendations
        .collect(Collectors.toList());
  }

  @Override
  public List<DutyRecommendationDto> getRecommendationsByLifeStage(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    if (user.getLifeStage() == null) {
      updateUserLifeStage(user);
    }
    List<Duties> duties =
        dutiesRepository.findByTargetLifeStageAndIsActiveTrue(user.getLifeStage());
    List<UserDutyProgress> userProgress = userDutyProgressRepository.findByUserId(userId);
    Map<Long, UserDutyProgress> progressMap =
        userProgress.stream().collect(Collectors.toMap(p -> p.getDuties().getId(), p -> p));
    return duties.stream()
        .map(duty -> convertToDutyRecommendationDto(duty, user, progressMap.get(duty.getId())))
        .sorted((a, b) -> getPriorityWeight(a.getPriority()) - getPriorityWeight(b.getPriority()))
        .collect(Collectors.toList());
  }

  @Override
  public List<DutyRecommendationDto> getRecommendationsByInterests(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    List<Duties> duties = dutiesRepository.findByUserInterests(userId);
    List<UserDutyProgress> userProgress = userDutyProgressRepository.findByUserId(userId);
    Map<Long, UserDutyProgress> progressMap =
        userProgress.stream().collect(Collectors.toMap(p -> p.getDuties().getId(), p -> p));

    return duties.stream()
        .map(duty -> convertToDutyRecommendationDto(duty, user, progressMap.get(duty.getId())))
        .collect(Collectors.toList());
  }

  @Override
  public List<DutyRecommendationDto> getCriticalRecommendations(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    List<Duties> criticalDuties = dutiesRepository.findByPriorityAndIsActiveTrue(Priority.CRITICAL);
    List<Duties> highDuties = dutiesRepository.findByPriorityAndIsActiveTrue(Priority.HIGH);

    List<Duties> allCriticalDuties = new ArrayList<>();
    allCriticalDuties.addAll(criticalDuties);
    allCriticalDuties.addAll(highDuties);

    List<UserDutyProgress> userProgress = userDutyProgressRepository.findByUserId(userId);
    Map<Long, UserDutyProgress> progressMap =
        userProgress.stream().collect(Collectors.toMap(p -> p.getDuties().getId(), p -> p));

    return allCriticalDuties.stream()
        .filter(duty -> isApplicableToUser(duty, user))
        .map(duty -> convertToDutyRecommendationDto(duty, user, progressMap.get(duty.getId())))
        .sorted((a, b) -> getPriorityWeight(a.getPriority()) - getPriorityWeight(b.getPriority()))
        .toList();
  }

  @Override
  public void updateUserLifeStage(User user) {
    if (user.getDateOfBirth() != null) {
      int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
      LifeStage newLifeStage = determineLifeStage(age, user);

      if (user.getLifeStage() != newLifeStage) {
        user.setLifeStage(newLifeStage);
        userRepository.save(user);
        log.info("Updated life stage for user {} to {}", user.getEmail(), newLifeStage);
      }
    }
  }

  private DutyRecommendationDto convertToDutyRecommendationDto(
      Duties duty, User user, UserDutyProgress progress) {
    String reasonForRecommendation = generateReasonForRecommendation(duty, user);
    int matchScore = calculateMatchScore(duty, user);

    return DutyRecommendationDto.builder()
        .id(duty.getId())
        .title(duty.getTitle())
        .description(duty.getDescription())
        .category(duty.getCategory().getName())
        .priority(duty.getPriority())
        .estimatedCost(duty.getEstimatedCost())
        .timeToComplete(duty.getTimeToComplete())
        .reasonForRecommendation(reasonForRecommendation)
        .matchScore(matchScore)
        .isCompleted(progress != null && progress.getStatus() == ProgressStatus.COMPLETED)
        .isInProgress(progress != null && progress.getStatus() == ProgressStatus.IN_PROGRESS)
        .build();
  }

  private String generateReasonForRecommendation(Duties duty, User user) {
    List<String> reasons = new ArrayList<>();

    if (duty.getTargetLifeStage() == user.getLifeStage()) {
      reasons.add(
          "Perfect for your current life stage ("
              + user.getLifeStage().name().toLowerCase().replace("_", " ")
              + ")");
    }

    if (duty.getPriority() == Priority.CRITICAL) {
      reasons.add("Critical for financial security");
    } else if (duty.getPriority() == Priority.HIGH) {
      reasons.add("High priority for your age group");
    }

    if (user.getDateOfBirth() != null) {
      int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
      if (duty.getMinAge() != null
          && duty.getMaxAge() != null
          && age >= duty.getMinAge()
          && age <= duty.getMaxAge()) {
        reasons.add("Recommended for your age group (" + age + " years)");
      }
    }

    return reasons.isEmpty() ? "Recommended based on your profile" : String.join(". ", reasons);
  }

  private int calculateMatchScore(Duties duty, User user) {
    int score = 50; // Base score

    // Life stage match
    if (duty.getTargetLifeStage() == user.getLifeStage()) {
      score += 30;
    }

    // Age range match
    if (user.getDateOfBirth() != null) {
      int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
      if (duty.getMinAge() != null
          && duty.getMaxAge() != null
          && age >= duty.getMinAge()
          && age <= duty.getMaxAge()) {
        score += 20;
      }
    }

    // Priority boost
    switch (duty.getPriority()) {
      case CRITICAL -> score += 25;
      case HIGH -> score += 15;
      case MEDIUM -> score += 5;
      default -> score += 0;
    }

    return Math.min(100, score);
  }

  private boolean isApplicableToUser(Duties duty, User user) {
    // Check age range
    if (user.getDateOfBirth() != null) {
      int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
      if (duty.getMinAge() != null && age < duty.getMinAge()) return false;
      if (duty.getMaxAge() != null && age > duty.getMaxAge()) return false;
    }

    // Check life stage
    if (duty.getTargetLifeStage() != null && user.getLifeStage() != null) {
      return duty.getTargetLifeStage() == user.getLifeStage();
    }

    return true;
  }

  private LifeStage determineLifeStage(int age, User user) {
    // Consider both age and other factors like occupation, income
    if (age < 22
        || (user.getCurrentOccupation() != null
            && user.getCurrentOccupation().toLowerCase().contains("student"))) {
      return LifeStage.STUDENT;
    } else {
        return getLifeStage(age);
    }
  }
    private int getPriorityWeight(Priority priority) {
    return switch (priority) {
      case CRITICAL -> 1;
      case HIGH -> 2;
      case MEDIUM -> 3;
      case LOW -> 4;
      case OPTIONAL -> 5;
    };
  }
}
