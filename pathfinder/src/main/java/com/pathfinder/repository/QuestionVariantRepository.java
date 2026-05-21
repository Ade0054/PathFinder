package com.pathfinder.repository;

import com.pathfinder.model.QuestionVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionVariantRepository extends JpaRepository<QuestionVariant, Long> {
    List<QuestionVariant> findByQuestion_QuestionId(Long questionId);
}
