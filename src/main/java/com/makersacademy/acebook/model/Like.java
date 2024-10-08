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
    private Long userId;


//    @Column(name = "postId", insertable = false, updatable = false)
//    private Long postId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Like() {
        this.createdAt = LocalDateTime.now();
    }

    public Like(Long user_id, Post post) {
        this.userId = user_id;
        this.post = post;
        this.createdAt = LocalDateTime.now();
    }

}
