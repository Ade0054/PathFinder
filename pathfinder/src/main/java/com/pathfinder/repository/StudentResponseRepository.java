package com.pathfinder.repository;

import com.pathfinder.model.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentResponseRepository extends JpaRepository<StudentResponse, Long> {
    boolean existsByClientUuid(String clientUuid);     // idempotent offline sync
    List<StudentResponse> findByUser_UserId(Long userId);
}
