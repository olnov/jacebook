package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;


@Controller
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/users")
    public User afterRegistration(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @GetMapping("/profile/{id}")
    public String loadUserProfile(@PathVariable("id") Long id, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("user_id");
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        model.addAttribute("userId", userId);
        model.addAttribute("user", user);
        return "profile";
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity updateUserProfile(@PathVariable("id") Long id, @RequestParam("full_name") String full_name, @RequestParam("bio") String bio, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            throw new RuntimeException("Not authorized");
        }
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        if (Objects.equals(userId,id)) {
            user.setBio(bio);
            user.setFull_name(full_name);
            userRepository.save(user);
        }else{
            throw new RuntimeException("You are not allowed to modify this profile");
        }
        return ResponseEntity.ok("Updated successfully");
    }

//    @GetMapping("/users/loggedin")
//    public RedirectView afterLogin(){
//        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//
//        String username = (String) principal.getAttributes().get("email");
//        User currentUser = userRepository
//                .findByUsername(username)
//                .orElseGet(() -> userRepository.save(new User(username)));
//        return new RedirectView("/posts");
//    }
}
