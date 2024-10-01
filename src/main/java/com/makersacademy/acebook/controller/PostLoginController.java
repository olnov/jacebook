package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class PostLoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/post-login")
    public String handlePostLogin(Authentication authentication, HttpSession session) {
        // Get email from Auth0 (principal's attributes)
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String email = oidcUser.getAttribute("email");

        // Simulate fetching user from the database (assuming email is stored in username column)
        Optional<User> userOptional = userRepository.findByUsername(email);
        if (userOptional.isPresent()) {
            Long userId = userOptional.get().getId();

            // Store the user_id in HttpSession
            session.setAttribute("user_id", userId);

            // Optionally redirect to another page after login
            return "redirect:/posts";
        } else {
            throw new RuntimeException("User not found in the local database.");
        }
    }
}
