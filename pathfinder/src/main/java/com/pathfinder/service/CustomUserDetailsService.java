package com.pathfinder.service;

import com.pathfinder.model.User;
import com.pathfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user: " + username));

        // Spring Security expects authorities prefixed with ROLE_
        var authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())   // already a BCrypt hash
                .authorities(List.of(authority))
                .build();
    }
}
