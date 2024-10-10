package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.service.ImageService;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/upload/{userId}")
    public RedirectView uploadImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            // Check if the user exists
            User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));

            // Upload image to IDrive e2 and get image URL
            String imageUrl = imageService.uploadImage(file.getInputStream(), file.getSize(), file.getContentType());

            // Save the image URL in the user's profile
            user.setUser_photo(imageUrl);
            userRepository.save(user);

            return new RedirectView("/profile/"+userId);

        } catch (IOException e) {
            throw new RuntimeException("Error uploading file:"+e.getMessage());
        }
    }

    @PostMapping("/upload/post")
    public RedirectView uploadImageAsPost(@RequestParam("file") MultipartFile file, HttpSession session){
        try {
            Long userId = (Long) session.getAttribute("user_id");
            if (userId == null) {
                throw new RuntimeException("Not authorized");
            }

            String imageUrl = imageService.uploadImage(file.getInputStream(), file.getSize(), file.getContentType());
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Post post = new Post(imageUrl,user,true);
            postRepository.save(post);
            return new RedirectView("/");
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file:"+e.getMessage());
        }
    }
}
