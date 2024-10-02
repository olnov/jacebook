package com.makersacademy.acebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavbarController {

    @GetMapping("/friends")
    public String friends() {
        return "friends";
    }

    @GetMapping("/my-posts")
    public String myPosts() {
        return "my-posts";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}