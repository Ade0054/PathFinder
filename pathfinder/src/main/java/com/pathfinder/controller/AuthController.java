package com.pathfinder.controller;

import com.pathfinder.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /** Step 1: choose Student or Counsellor. Step 2: the matching form. */
    @GetMapping("/register")
    public String registerChoice() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String email,
                             @RequestParam String role,           // STUDENT or COUNSELLOR
                             @RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String lastName,
                             Model model) {
        try {
            authService.register(username, password, email, role, firstName, lastName);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }
}
