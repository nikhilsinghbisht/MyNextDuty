package com.mynextduty.core.config.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * Service for Redis operations using Redisson client. Provides distributed locking functionality.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedissonClient redissonClient;

  /**
   * Attempts to acquire a distributed lock.
   *
   * @param lockKey the key for the lock
   * @param timeoutSeconds timeout in seconds for the lock
   * @return true if lock acquired successfully, false otherwise
   */
  public boolean getLock(String lockKey, int timeoutSeconds) {
    try {
      RLock lock = redissonClient.getLock(lockKey);
      return lock.tryLock(0, timeoutSeconds, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.warn("Thread interrupted while acquiring lock: {}", lockKey);
      return false;
    } catch (Exception e) {
      log.error("Error acquiring lock: {}", lockKey, e);
      return false;
    }
  }

  /**
   * Releases a distributed lock.
   *
   * @param lockKey the key for the lock to release
   */
  public void releaseLock(String lockKey) {
    try {
      RLock lock = redissonClient.getLock(lockKey);
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
        log.debug("Lock released successfully: {}", lockKey);
      } else {
        log.warn("Attempted to release lock not held by current thread: {}", lockKey);
      }
    } catch (Exception e) {
      log.error("Error releasing lock: {}", lockKey, e);
    }
  }

  /**
   * Checks if a lock is currently held.
   *
   * @param lockKey the key for the lock
   * @return true if lock is held, false otherwise
   */
  public boolean isLocked(String lockKey) {
    try {
      RLock lock = redissonClient.getLock(lockKey);
      return lock.isLocked();
    } catch (Exception e) {
      log.error("Error checking lock status: {}", lockKey, e);
      return false;
    }
  }
}
