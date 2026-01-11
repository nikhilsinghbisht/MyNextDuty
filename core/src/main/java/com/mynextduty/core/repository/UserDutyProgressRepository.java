package com.mynextduty.core.repository;

import com.mynextduty.core.entity.UserDutyProgress;
import com.mynextduty.core.enums.ProgressStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDutyProgressRepository extends JpaRepository<UserDutyProgress, Long> {

  List<UserDutyProgress> findByUserId(Long userId);

  List<UserDutyProgress> findByUserIdAndStatus(Long userId, ProgressStatus status);

  Optional<UserDutyProgress> findByUserIdAndDutiesId(Long userId, Long dutyId);

  @Query(
      "SELECT COUNT(udp) FROM UserDutyProgress udp WHERE udp.user.id = :userId AND udp.status = :status")
  long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") ProgressStatus status);

  @Query(
      "SELECT udp FROM UserDutyProgress udp WHERE udp.user.id = :userId "
          + "ORDER BY udp.duties.priority ASC, udp.createdAt DESC")
  List<UserDutyProgress> findByUserIdOrderByPriorityAndDate(@Param("userId") Long userId);
}
