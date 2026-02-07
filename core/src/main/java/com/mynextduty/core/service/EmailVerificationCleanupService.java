package com.mynextduty.core.service;

import com.mynextduty.core.repository.EmailVerificationTokenRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to clean up expired email verification tokens.
 * Runs periodically to maintain database hygiene.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationCleanupService {

  private final EmailVerificationTokenRepository tokenRepository;

  /**
   * Clean up expired tokens every day at 2 AM
   */
  @Scheduled(cron = "0 0 2 * * ?")
  @Transactional
  public void cleanupExpiredTokens() {
    try {
      tokenRepository.deleteExpiredTokens(LocalDateTime.now());
      log.info("Cleaned up expired email verification tokens");
    } catch (Exception e) {
      log.error("Failed to cleanup expired tokens", e);
    }
  }
}