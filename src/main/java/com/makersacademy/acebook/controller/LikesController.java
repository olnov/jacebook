package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Like;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.LikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
public class LikesController {

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/like")
    public RedirectView createLike(@RequestParam("post_id") Long post_id, HttpSession session) {
        System.out.println("THE FRONT END HAS MADE THE REQUEST");
        Long userId = (Long) session.getAttribute("user_id");
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        postRepository.findById(post_id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Like like = new Like(userId, post_id);

        likeRepository.save(like);
        return new RedirectView("/");
    }

//    @DeleteMapping("/likes-delete")
//    public RedirectView deleteLike(@RequestParam("post_id") Long post_id, HttpSession session) {
//        Long userId = (Long) session.getAttribute("user_id");
//        Like like = likeRepository.findByUser_idAndPost_id(userId, post_id)
//                .orElseThrow(() -> new RuntimeException("like not found"));
//        likeRepository.delete(like);
//        return new RedirectView("/");
//    }
}