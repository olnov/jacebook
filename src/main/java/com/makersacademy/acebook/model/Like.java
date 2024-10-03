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
    private Long user_id;
    private Long post_id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Like() {
        this.createdAt = LocalDateTime.now();
    }

    public Like(Long user_id, Long post_id) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.createdAt = LocalDateTime.now();
    }

}
