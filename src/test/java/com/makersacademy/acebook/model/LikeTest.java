package com.makersacademy.acebook.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LikeTest {

    private Long userId1 = Long.valueOf(1);
    private Long userId2 = Long.valueOf(2);
    private Long postId = Long.valueOf(2);

    // Test objects
    private Like like;
    private User testUser;
    private Post testPost;
    private LocalDateTime testCreatedAt;

    @BeforeEach
    public void setUp() {
        like = new Like(); // Initialize a new Like instance
        testUser = new User(); // Assuming you have a User class
        testPost = new Post(); // Assuming you have a Post class
        testCreatedAt = LocalDateTime.now();
    }

    @Test
    public void likeCountIncrementsWhenMoreLikesAreAdded() {
        // Simulating a list of likes on a post
        List<Like> likesOnPost = new ArrayList<>();

        // Initially, no likes
        assertThat(likesOnPost.size(), is(0));

        // User 1 likes the post
        Like like1 = new Like(userId1, postId);
        likesOnPost.add(like1);
        assertThat(likesOnPost.size(), is(1)); // Expecting 1 like now

        // User 2 likes the post
        Like like2 = new Like(userId2, postId);
        likesOnPost.add(like2);
        assertThat(likesOnPost.size(), is(2)); // Expecting 2 likes now
    }

    @Test
    public void userCanLikeMultiplePosts() {
        List<Like> likesOnPost = new ArrayList<>();

        // User 1 likes post 1
        Like like1 = new Like(userId1, Long.valueOf(1));
        likesOnPost.add(like1);

        // User 1 likes post 2
        Like like2 = new Like(userId1, Long.valueOf(2));
        likesOnPost.add(like2);

        // Expecting 2 likes since user 1 liked two different posts
        assertThat(likesOnPost.size(), is(2));
    }

    @Test
    public void testSetAndGetId() {
        like.setId(userId1);
        assertThat(like.getId(), is(userId1));
    }

    @Test
    public void testSetAndGetPost() {
        like.setPost(testPost);
        assertThat(like.getPost(), is(testPost));
    }

    @Test
    public void testSetAndGetUser() {
        like.setUser(testUser);
        assertThat(like.getUser(), is(testUser));
    }

    @Test
    public void testSetAndGetCreatedAt() {
        like.setCreatedAt(testCreatedAt);
        assertThat(like.getCreatedAt(), is(testCreatedAt));
    }

    @Test
    public void testConstructorWithUserAndPost() {
        Like likeWithParams = new Like(testUser, testPost);
        assertThat(likeWithParams.getUser(), is(testUser));
        assertThat(likeWithParams.getPost(), is(testPost));
        assertThat(likeWithParams.getCreatedAt(), is(notNullValue())); // Ensure createdAt is set
    }
}
