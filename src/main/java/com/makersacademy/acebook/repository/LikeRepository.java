package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

}



// SQL to add: logged in user to likes table for each respective post
// Logged in user clicks like, that user_id is added to post
// SQL to retrieve and count num of user_ids associated to specific post
