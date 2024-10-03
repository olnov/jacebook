package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Like;
import com.makersacademy.acebook.repository.LikeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
public class LikesController {

    @Autowired
    LikeRepository likeRepository;


}

// Controller
// Post mapping - When user clicks 'like', post mapping posts to likes table and adds user_id to respective post_id
// get mapping - Retrieving how many user_ids are related to that post
//