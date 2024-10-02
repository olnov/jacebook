package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Post() {
        this.createdAt = LocalDateTime.now();
    }

    public Post(String content) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
    public String getContent() { return this.content; }
    public void setContent(String content) { this.content = content; }

}
