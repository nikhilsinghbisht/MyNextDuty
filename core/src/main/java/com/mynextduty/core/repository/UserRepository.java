package com.mynextduty.core.repository;

import com.mynextduty.core.entity.User;
import com.mynextduty.core.enums.LifeStage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  List<User> findByLifeStage(LifeStage lifeStage);

  @Query(
      "SELECT u FROM User u WHERE u.dateOfBirth IS NOT NULL AND "
          + "FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', u.dateOfBirth) BETWEEN :minAge AND :maxAge")
  List<User> findByAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

  boolean existsByEmail(String email);
}
