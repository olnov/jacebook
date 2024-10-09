package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@Table(name = "LIKES")

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Like() {
        this.createdAt = LocalDateTime.now();
    }

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
        this.createdAt = LocalDateTime.now();
    }
//  constructor for liketest
    public Like(Long userId2, Long postId) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
