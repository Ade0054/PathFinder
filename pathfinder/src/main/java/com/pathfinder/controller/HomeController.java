package com.pathfinder.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    /** After login, send each role to its own homepage. */
    @GetMapping("/home")
    public String home(Authentication auth) {
        boolean isCounsellor = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_COUNSELLOR"));
        return isCounsellor ? "redirect:/counsellor/home" : "redirect:/student/home";
    }
}
