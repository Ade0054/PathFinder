package com.pathfinder.controller;

import com.pathfinder.model.Classroom;
import com.pathfinder.model.User;
import com.pathfinder.repository.ClassroomRepository;
import com.pathfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CounsellorController {

    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    private static final String ALPHANUM = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"; // no confusing 0/O/1/I
    private static final SecureRandom RANDOM = new SecureRandom();

    @GetMapping("/counsellor/home")
    public String home(Authentication auth, Model model) {
        User counsellor = userRepository.findByUsername(auth.getName()).orElseThrow();
        List<Classroom> classrooms =
                classroomRepository.findByCounsellor_UserId(counsellor.getUserId());
        model.addAttribute("username", counsellor.getUsername());
        model.addAttribute("classrooms", classrooms);
        return "counsellor/home";
    }

    @PostMapping("/counsellor/classroom/create")
    public String createClassroom(@RequestParam String classroomName, Authentication auth) {
        User counsellor = userRepository.findByUsername(auth.getName()).orElseThrow();
        Classroom classroom = Classroom.builder()
                .classroomName(classroomName)
                .joinCode(generateUniqueJoinCode())
                .counsellor(counsellor)
                .createdAt(Instant.now())
                .build();
        classroomRepository.save(classroom);
        return "redirect:/counsellor/home";
    }

    /** Generates a short code like MCP-7K2QH9; retries until unique. */
    private String generateUniqueJoinCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder("MCP-");
            for (int i = 0; i < 6; i++) {
                sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
            }
            code = sb.toString();
        } while (classroomRepository.existsByJoinCode(code));
        return code;
    }
}
