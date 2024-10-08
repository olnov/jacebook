package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import jakarta.servlet.http.HttpSession;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Objects;

@Controller
public class PostsController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        Long userId = (Long) session.getAttribute("user_id");
        User currentUser = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("userId", userId);
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());
        model.addAttribute("comment", new Comment());
        return "feed";
    }


    @GetMapping("/my-posts")
    public String myPosts(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("user_id");
        List<Post> myPostList = postRepository.findAllByUserId(userId);
        User currentUser = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("userId", userId);
        model.addAttribute("posts", myPostList);
        model.addAttribute("post", new Post());
        return "feed";
    }

    @PostMapping("/posts")
    public RedirectView create(@RequestParam("content") String content, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post(content,user,false);
        postRepository.save(post);
//        return  new RedirectView("/posts");
        return new RedirectView("/");
    }

    @DeleteMapping("/posts/{post_id}")
    public ResponseEntity<String> deletePost(@PathVariable("post_id") Long post_id, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            throw new RuntimeException("Not authorized");
        }
        Post post = postRepository.findById(post_id)
                .orElseThrow(()-> new RuntimeException("Post not found"));
        if (Objects.equals(post.getUser().getId(), userId)) {
            postRepository.deleteById(post_id);
        } else {
            throw new RuntimeException("You are not allowed to delete this post");
        }
        return ResponseEntity.ok("Deleted successfully");
    }

    @PutMapping("/posts/{post_id}")
    public ResponseEntity<String> editPost(@PathVariable("post_id") Long post_id, @RequestParam("content") String content, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null){
            throw new RuntimeException("Not authorized");
        }
        Post existingPost = postRepository.findById(post_id).
                orElseThrow(()-> new RuntimeException("Post not found"));
        if (Objects.equals(existingPost.getUser().getId(),userId)) {
            existingPost.setContent(content);
            postRepository.save(existingPost);
        }
        return ResponseEntity.ok("Post updated successfully");
    }
}
