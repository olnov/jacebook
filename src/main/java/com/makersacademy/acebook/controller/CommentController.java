
package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @PostMapping("/comments")
    public ResponseEntity<String> createPost(@RequestParam("content") String content, @RequestParam("postId") Long post_id, HttpSession session) {
        System.out.println("hello");
        System.out.println(content);
        System.out.println(post_id);
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            throw new RuntimeException("Not authorized");
        }
        Comment comment = new Comment();
        comment.setContent(content);
        //Long user_id = 1L;
        User user = userRepository.findById(userId).get();
        comment.setUser(user);
        //Long post_id = 1L;
        Post post = postRepository.findById(post_id).get();
        comment.setPost(post);

        commentRepository.save(comment);
        return ResponseEntity.ok("Created successfully");
    }

    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<String> deleteComment(@PathVariable(name="comment_id") Long comment_id, HttpSession session){
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            throw new RuntimeException("Not authorized");
        }
        Comment comment = commentRepository.findById(comment_id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid comment id:" + comment_id));
        commentRepository.delete(comment);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PutMapping("/comments/{comment_id}")
    public ResponseEntity<String> updateComment(@PathVariable(name="comment_id") Long comment_id, @RequestParam(name="content") String content, HttpSession session){
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            throw new RuntimeException("Not authorized");
        }
        Comment comment = commentRepository.findById(comment_id).get();
        comment.setContent(content);
        commentRepository.save(comment);
        return ResponseEntity.ok("Comment updated successfully");
    }
}
