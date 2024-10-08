package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.Optional;
import java.util.Set;

@Data
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;


    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "post")
    private Set<Like> likes;




    // If posts.likes returns list of all likes that belong to post
    // use hibernate / JPA @OneToMany
    // Then check to see if one of those likes was created by current user
    // This should be method on Post.java

    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    public Post() {
//        this.createdAt = LocalDateTime.now();
//    }

    public Post() {}


    public Post(String content, User user) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public String getContent() { return this.content; }
    public void setContent(String content) { this.content = content; }
    public User getUser() { return user; }
    public void setUser() { this.user = user; }

// ======= Explicit setter and getter ======== //

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Long getId() {
        return this.id;
    }

}
