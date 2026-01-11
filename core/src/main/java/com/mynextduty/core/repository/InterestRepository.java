package com.mynextduty.core.repository;

import com.mynextduty.core.entity.Interest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
  List<Interest> findByCategoryId(Long categoryId);

  boolean existsByName(String name);
}
