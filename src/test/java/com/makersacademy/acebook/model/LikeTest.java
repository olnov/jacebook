//package com.makersacademy.acebook.model;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//public class LikeTest {
//
//    private Long userId1 = Long.valueOf(1);
//    private Long userId2 = Long.valueOf(2);
//    private Long postId = Long.valueOf(2);
//
//    @Test
//    public void likeCountIncrementsWhenMoreLikesAreAdded() {
//        // Simulating a list of likes on a post
//        List<Like> likesOnPost = new ArrayList<>();
//
//        // Initially, no likes
//        assertThat(likesOnPost.size(), is(0));
//
//        // User 1 likes the post
//        Like like1 = new Like(userId1, postId);
//        likesOnPost.add(like1);
//        assertThat(likesOnPost.size(), is(1)); // Expecting 1 like now
//
//        // User 2 likes the post
//        Like like2 = new Like(userId2, postId);
//        likesOnPost.add(like2);
//        assertThat(likesOnPost.size(), is(2)); // Expecting 2 likes now
//    }
//
//    // Additional test examples could go here, e.g., testing field content or timestamp validation
//}
