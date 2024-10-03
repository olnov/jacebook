
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
    //private Long post_id;
    //private Long user_id;

    //add line here for foreign key of user id
    @ManyToOne
    @JoinColumn(name="post_id")//, referencedColumnName = "id")
    private Post post;
    //add line here for foreign key of post id
    @ManyToOne
    @JoinColumn(name="user_id")//, referencedColumnName = "id")
    private User user;

    public Comment() {}

    public Comment(String content, Long post_id, Long user_id) {
        this.content = content;
        //this.post_id = post_id;
        //this.user_id = user_id;
    }

}
