package com.makersacademy.acebook.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/check-session")
    public String checkUserIdInSession(HttpSession session) {
        // Check if 'user_id' exists in the session
        Object userId = session.getAttribute("user_id");

        if (userId != null) {
            return "User ID in session: " + userId;
        } else {
            return "No user ID found in session.";
        }
    }
}

