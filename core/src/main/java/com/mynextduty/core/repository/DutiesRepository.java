package com.mynextduty.core.repository;

import com.mynextduty.core.entity.Duties;
import com.mynextduty.core.enums.LifeStage;
import com.mynextduty.core.enums.Priority;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DutiesRepository extends JpaRepository<Duties, Long> {

  List<Duties> findByTargetLifeStageAndIsActiveTrue(LifeStage lifeStage);

  List<Duties> findByPriorityAndIsActiveTrue(Priority priority);

  @Query(
      "SELECT d FROM Duties d WHERE d.isActive = true AND "
          + "(d.minAge IS NULL OR :age >= d.minAge) AND "
          + "(d.maxAge IS NULL OR :age <= d.maxAge)")
  List<Duties> findByAgeRange(@Param("age") int age);

  @Query(
      "SELECT d FROM Duties d JOIN d.relatedInterests i JOIN i.userInterests ui "
          + "WHERE ui.user.id = :userId AND d.isActive = true")
  List<Duties> findByUserInterests(@Param("userId") Long userId);

  @Query("SELECT d FROM Duties d WHERE d.category.id = :categoryId AND d.isActive = true")
  List<Duties> findByCategoryId(@Param("categoryId") Long categoryId);
}
