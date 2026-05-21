package com.pathfinder.controller;

import com.pathfinder.model.Classroom;
import com.pathfinder.model.ClassroomMembership;
import com.pathfinder.model.User;
import com.pathfinder.repository.ClassroomMembershipRepository;
import com.pathfinder.repository.ClassroomRepository;
import com.pathfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final UserRepository userRepository;
    private final ClassroomMembershipRepository membershipRepository;
    private final ClassroomRepository classroomRepository;

    @GetMapping("/student/home")
    public String home(Authentication auth, Model model) {
        User student = userRepository.findByUsername(auth.getName()).orElseThrow();
        List<ClassroomMembership> memberships =
                membershipRepository.findByStudent_UserId(student.getUserId());

        model.addAttribute("username", student.getUsername());
        model.addAttribute("memberships", memberships);
        model.addAttribute("hasClassroom", !memberships.isEmpty());
        return "student/home";
    }

    @GetMapping("/student/join")
    public String joinPage() {
        return "student/join";
    }

    @PostMapping("/student/join")
    public String join(@RequestParam String joinCode, Authentication auth, Model model) {
        User student = userRepository.findByUsername(auth.getName()).orElseThrow();
        Optional<Classroom> classroom =
                classroomRepository.findByJoinCode(joinCode.trim().toUpperCase());

        if (classroom.isEmpty()) {
            model.addAttribute("error", "No classroom found with that code.");
            return "student/join";
        }
        Long classroomId = classroom.get().getClassroomId();
        boolean already = membershipRepository
                .existsByClassroom_ClassroomIdAndStudent_UserId(classroomId, student.getUserId());
        if (!already) {
            membershipRepository.save(ClassroomMembership.builder()
                    .classroom(classroom.get())
                    .student(student)
                    .joinedAt(Instant.now())
                    .build());
        }
        return "redirect:/student/home";
    }
}
