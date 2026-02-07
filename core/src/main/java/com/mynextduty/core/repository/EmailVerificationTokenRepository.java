package com.mynextduty.core.repository;

import com.mynextduty.core.entity.EmailVerificationToken;
import com.mynextduty.core.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing email verification tokens.
 */
@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

  /**
   * Find a token by its string value
   */
  Optional<EmailVerificationToken> findByToken(String token);

  /**
   * Find all unused tokens for a specific user
   */
  List<EmailVerificationToken> findByUserAndUsedFalse(User user);

  /**
   * Mark all unused tokens for a user as used (for invalidation)
   */
  @Modifying
  @Query("UPDATE EmailVerificationToken t SET t.used = true WHERE t.user = :user AND t.used = false")
  void markAllTokensAsUsedForUser(@Param("user") User user);

  /**
   * Delete expired tokens (cleanup job)
   */
  @Modifying
  @Query("DELETE FROM EmailVerificationToken t WHERE t.expiresAt < :now")
  void deleteExpiredTokens(@Param("now") LocalDateTime now);
}