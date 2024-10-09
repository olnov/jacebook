package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.model.UserDTO;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String getAllUsers(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("user_id");

        if (userId == null) {
            return "redirect:/login";
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Fetch user's friends and convert them to UserDTO to avoid circular reference
            List<UserDTO> friends = user.getFriends().stream()
                    .map(friend -> new UserDTO(friend.getId(), friend.getUsername()))
                    .collect(Collectors.toList());
            model.addAttribute("friends", friends);
        } else {
            return "error";
        }

        // Fetch all users and convert them to UserDTO
        List<UserDTO> users = userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
        model.addAttribute("users", users);

        return "friends";
    }





}