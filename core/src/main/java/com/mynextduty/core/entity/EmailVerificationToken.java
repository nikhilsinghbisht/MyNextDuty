package com.mynextduty.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing email verification tokens.
 * Used to verify user email addresses during registration and resend flows.
 */
@Entity
@Table(name = "email_verification_tokens")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationToken {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String token;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "expires_at", nullable = false)
  private LocalDateTime expiresAt;

  @Column(nullable = false)
  @Builder.Default
  private boolean used = false;

  @Column(name = "created_at", nullable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  /**
   * Checks if the token is expired
   */
  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expiresAt);
  }

  /**
   * Checks if the token is valid (not used and not expired)
   */
  public boolean isValid() {
    return !used && !isExpired();
  }
}