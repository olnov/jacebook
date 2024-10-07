package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
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
    private Long postId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Like() {
        this.createdAt = LocalDateTime.now();
    }

    public Like(Long user_id, Long post_id) {
        this.userId = user_id;
        this.postId = post_id;
        this.createdAt = LocalDateTime.now();
    }

}
