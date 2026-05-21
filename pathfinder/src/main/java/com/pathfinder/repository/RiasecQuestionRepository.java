package com.pathfinder.repository;

import com.pathfinder.model.RiasecQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RiasecQuestionRepository extends JpaRepository<RiasecQuestion, Long> {
    List<RiasecQuestion> findByCategory_CategoryId(Long categoryId);
}
