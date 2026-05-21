package com.pathfinder.repository;

import com.pathfinder.model.ClassroomTestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassroomTestStatusRepository extends JpaRepository<ClassroomTestStatus, Long> {
    List<ClassroomTestStatus> findByClassroom_ClassroomId(Long classroomId);
}
