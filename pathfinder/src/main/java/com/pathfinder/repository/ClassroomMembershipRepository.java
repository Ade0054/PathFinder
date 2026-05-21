package com.pathfinder.repository;

import com.pathfinder.model.ClassroomMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassroomMembershipRepository extends JpaRepository<ClassroomMembership, Long> {
    List<ClassroomMembership> findByStudent_UserId(Long studentId);
    List<ClassroomMembership> findByClassroom_ClassroomId(Long classroomId);
    boolean existsByClassroom_ClassroomIdAndStudent_UserId(Long classroomId, Long studentId);
}
