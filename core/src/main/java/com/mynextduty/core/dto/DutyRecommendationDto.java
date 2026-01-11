package com.mynextduty.core.dto;

import com.mynextduty.core.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DutyRecommendationDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private Priority priority;
    private Double estimatedCost;
    private String timeToComplete;
    private String reasonForRecommendation;
    private Integer matchScore; // 1-100 based on user profile match
    private boolean isCompleted;
    private boolean isInProgress;
}