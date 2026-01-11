package com.mynextduty.core.repository;

import com.mynextduty.core.entity.EducationLevel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationLevelRepository extends JpaRepository<EducationLevel, Long> {
  Optional<EducationLevel> findByLevelCode(String levelCode);

  boolean existsByLevelCode(String levelCode);
}
