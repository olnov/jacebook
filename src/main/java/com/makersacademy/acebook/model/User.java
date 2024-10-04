package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Data
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public User() {
        this.enabled = TRUE;
    }

    public User(String username) {
        this.username = username;
        this.enabled = TRUE;
    }

    public User(String username, boolean enabled) {
        this.username = username;
        this.enabled = enabled;
    }

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
