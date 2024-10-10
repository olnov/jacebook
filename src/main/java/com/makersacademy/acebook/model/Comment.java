
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
    @JoinColumn(name="post_id", referencedColumnName = "id")
    private Post post;
    //add line here for foreign key of post id
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    public Comment() {}

    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
