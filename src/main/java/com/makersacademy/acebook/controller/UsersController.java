package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.List;

@RestController
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/users")
    public User afterRegistration(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    // Add a friend
    @PostMapping("/users/{userId}/friends/{friendId}")
    public String addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> friendOptional = userRepository.findById(friendId);

        if (userOptional.isPresent() && friendOptional.isPresent()) {
            User user = userOptional.get();
            User friend = friendOptional.get();
            user.addFriend(friend); // Add friend
            userRepository.save(user); // Save the user with the updated friends list
            return "Friend added successfully";
        } else {
            return "User or friend not found";
        }
    }

    // Remove a friend
    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public String removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> friendOptional = userRepository.findById(friendId);

        if (userOptional.isPresent() && friendOptional.isPresent()) {
            User user = userOptional.get();
            User friend = friendOptional.get();
            user.removeFriend(friend); // Remove friend
            userRepository.save(user); // Save the user with the updated friends list
            return "Friend removed successfully";
        } else {
            return "User or friend not found";
        }
    }

    // Get the user's friends list
    @GetMapping("/users/{userId}/friends")
    public List<User> getFriendsList(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getFriends(); // Return the list of friends
        } else {
            return null; // Handle user not found
        }
    }
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

