package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    public Boolean isLikedBy(Long userId){
        List<Long> userIds = this.likes.stream().map(like -> like.getUser().getId()).toList();
        return userIds.contains(userId);
    }

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Post() {}


    public Post(String content, User user) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

// Content Getter and Setters
    public String getContent() { return this.content; }
    public void setContent(String content) { this.content = content; }


// User Getter and Setters
    public User getUser() { return user; }
    public void setUser() { this.user = user; }


// CreatedAt Getter and Setter
    public LocalDateTime getCreatedAt() {
    return this.createdAt;
}
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

 // ID Getter
    public Long getId() {
        return this.id;
    }

// Like Methods
    public int getLikeCount() {
        return this.likes.size();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }
}
