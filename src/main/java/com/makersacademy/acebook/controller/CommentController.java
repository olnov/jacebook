
package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public RedirectView create(@ModelAttribute Comment comment, @RequestParam(name = "post_id") Long post_id, @RequestParam(name = "user_id") Long user_id) {
        System.out.println(comment);
        //Long user_id = 1L;
        User user = userRepository.findById(user_id).get();
        comment.setUser(user);
        //Long post_id = 1L;
        Post post = postRepository.findById(post_id).get();
        comment.setPost(post);

        commentRepository.save(comment);
        return new RedirectView("/");
    }

    @GetMapping("/comments/delete")
    public RedirectView deleteComment(@RequestParam(name="comment_id") Long comment_id){
        Comment comment = commentRepository.findById(comment_id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid comment id:" + comment_id));
        commentRepository.delete(comment);
        return new RedirectView("/");
    }

    @PostMapping("/comments/update")
    public RedirectView updateComment(@RequestParam(name="comment_id") Long comment_id, @RequestParam(name="content") String content){
        Comment comment = commentRepository.findById(comment_id).get();
        comment.setContent(content);
        commentRepository.save(comment);
        return new RedirectView("/");
    }
}
