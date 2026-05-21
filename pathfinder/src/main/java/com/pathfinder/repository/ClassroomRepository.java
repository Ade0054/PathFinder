package com.pathfinder.repository;

import com.pathfinder.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByJoinCode(String joinCode);
    boolean existsByJoinCode(String joinCode);
    List<Classroom> findByCounsellor_UserId(Long counsellorId);
}
