package com.makersacademy.acebook.repository;

import com.makersacademy.acebook.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndPostId(Long user_id, Long post_id);
}



// SQL to add: logged in user to likes table for each respective post
// Logged in user clicks like, that user_id is added to post
// SQL to retrieve and count num of user_ids associated to specific post
