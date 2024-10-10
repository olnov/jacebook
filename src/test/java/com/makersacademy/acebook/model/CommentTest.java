package com.makersacademy.acebook.model;


// Importing static methods from Hamcrest for matchers and assertions
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test; // Importing the JUnit 5 Test annotation

public class CommentTest {

    private User testUser = new User("test@gmail.com");
    private Post post1 = new Post("post 1", testUser, false);
    private Comment comment1 = new Comment("comment 1", post1, testUser);

    @Test
    public void commentHasContent() {
        assertThat(comment1.getContent(), containsString("comment 1"));
    }

    @Test
    public void commentHasPost() {
        assertThat(comment1.getPost().getContent(), containsString("post 1"));
    }

    @Test
    public void commentHasUser() {
        assertThat(comment1.getUser().getUsername(), containsString("test@gmail.com"));
    }
}
