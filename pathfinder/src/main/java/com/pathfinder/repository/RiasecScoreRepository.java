package com.pathfinder.repository;

import com.pathfinder.model.RiasecScore;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RiasecScoreRepository extends JpaRepository<RiasecScore, Long> {
    List<RiasecScore> findByUser_UserId(Long userId);
}
