package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class NavbarController {
    @Autowired
    UserRepository userRepository;

//    @GetMapping("/my-posts")
//    public String myPosts() {
//        return "my-posts";
//    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/friends")
    public String getAllUsers(Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> currentUser = userRepository.findByUsername(currentUsername); // Find current user by username
        User user = currentUser.get();

        List<User> friends = user.getFriends(); // Get the user's friends
        model.addAttribute("friends", friends); // Add friends list to model

        List<User> users = userRepository.findAll(); // Fetch all users
        model.addAttribute("users", users); // Add users list to model

        return "friends"; // Return Thymeleaf template (friends.html)
    }


}