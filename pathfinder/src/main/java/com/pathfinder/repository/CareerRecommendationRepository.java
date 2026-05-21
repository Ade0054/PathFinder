package com.pathfinder.repository;

import com.pathfinder.model.CareerRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CareerRecommendationRepository extends JpaRepository<CareerRecommendation, Long> {
    List<CareerRecommendation> findByCategory_CategoryId(Long categoryId);
}
