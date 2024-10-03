package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import jakarta.servlet.http.HttpSession;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
//        Iterable<Post> posts = repository.findAll();
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        Long userId = (Long) session.getAttribute("user_id");
        model.addAttribute("userId", userId);
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());
        return "feed";
    }

    @GetMapping("/my-posts")
    public String myPosts(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("user_id");
        List<Post> myPostList = postRepository.findAllByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("posts", myPostList);
        model.addAttribute("post", new Post());
        return "feed";
    }
//    @PostMapping("/posts")
//    public RedirectView create(@ModelAttribute Post post) {
//        postRepository.save(post);
//        return new RedirectView("/posts");
//    }

    @PostMapping("/posts")
    public RedirectView create(@RequestParam("content") String content, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post(content,user);
        postRepository.save(post);
//        return  new RedirectView("/posts");
        return new RedirectView("/");
    }
}
