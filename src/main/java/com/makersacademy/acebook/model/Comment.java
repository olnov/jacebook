
package com.makersacademy.acebook.model;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    //add line here for foreign key of user id
    @ManyToOne
    @JoinColumn(name="post_id", referencedColumnName = "id")
    private Post post;
    //add line here for foreign key of post id
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    public Comment() {}

    public Comment(String content) {this.content = content;}

}
