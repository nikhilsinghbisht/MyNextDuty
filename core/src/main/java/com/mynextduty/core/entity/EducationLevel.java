package com.mynextduty.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "education_levels")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationLevel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String levelCode;

  @Column(nullable = false)
  private String levelName;

  private Integer levelRank; // 1-10 for ordering

  @Column(nullable = false)
  private boolean isActive = true;

  @OneToMany(mappedBy = "educationLevel", fetch = FetchType.LAZY)
  private List<User> users;

  @Builder.Default
  @Column(nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime updatedAt;

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
