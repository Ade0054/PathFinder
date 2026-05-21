package com.pathfinder.repository;

import com.pathfinder.model.RiasecCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RiasecCategoryRepository extends JpaRepository<RiasecCategory, Long> {
    Optional<RiasecCategory> findByCode(String code);
    boolean existsByCode(String code);
}
