package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Like;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.repository.LikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class LikesController {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/posts/{post_id}/like")
    public RedirectView createLike(@PathVariable("post_id") Long post_id, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Like like = new Like(userId, post);
        likeRepository.save(like);

        System.out.println("THE FRONT END HAS MADE THE REQUEST");
        List<Like> likes = likeRepository.findAllByPostId(post_id);
        System.out.println(likes);

        return new RedirectView("/");
    }

    @DeleteMapping("/posts/{post_id}/like")
    public ResponseEntity deleteLike(@PathVariable("post_id") Long post_id, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        Like like = likeRepository.findByUserIdAndPostId(userId, post_id)
                .orElseThrow(() -> new RuntimeException("like not found"));
        likeRepository.delete(like);
        return ResponseEntity.ok("Deleted like");
    }




    @GetMapping("/posts/{post_id}")
    public String listOfLikes(@PathVariable("post_id") Long postId, Model model) {
        List<Like> likes = likeRepository.findAllByPostId(postId);
        System.out.println("THE FRONT END HAS MADE THE REQUEST");
        model.addAttribute("likes", likes);

        if (likes.isEmpty()) {
            System.out.println("No likes found for post with id: " + postId);
        } else {
            System.out.println("Likes found for post with id: " + postId + ": " + likes);
        }

        System.out.println(likes);
        return "feed";
    }




}
