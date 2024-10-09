package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.service.ImageService;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
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
}
