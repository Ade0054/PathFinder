package com.pathfinder.service;

import com.pathfinder.model.Role;
import com.pathfinder.model.StudentProfile;
import com.pathfinder.model.User;
import com.pathfinder.repository.RoleRepository;
import com.pathfinder.repository.StudentProfileRepository;
import com.pathfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new user. roleName must be STUDENT or COUNSELLOR.
     * Throws IllegalArgumentException on duplicate username/email.
     */
    @Transactional
    public User register(String username, String rawPassword, String email,
                         RoleType roleType, String firstName, String lastName) {

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered.");
        }

        Role role = roleRepository.findByRoleName(roleType.name())
                .orElseThrow(() -> new IllegalArgumentException("Unknown role: " + roleType));

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))   // BCrypt hash
                .email(email)
                .role(role)
                .build();
        user = userRepository.save(user);

        // Students get a profile row
        if ("STUDENT".equals(roleType)) {
            StudentProfile profile = StudentProfile.builder()
                    .user(user)
                    .firstName(firstName)
                    .lastName(lastName)
                    .build();
            studentProfileRepository.save(profile);
        }
        return user;
    }
}
