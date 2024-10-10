package com.makersacademy.acebook.model;

// Importing static methods from Hamcrest for matchers and assertions
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class PostTest {

	private User testUser = new User("test@gmail.com");
	private Post post1 = new Post("First post", testUser);
	private Post post2 = new Post("Second post", testUser);
	private Post post = new Post("hello", testUser);

	// 1. Test the default constructor
	@Test
	public void defaultConstructorWorks() {
		Post newPost = new Post();
		assertNotNull(newPost); // Ensure the object is created
	}

	// 2. Test the parameterized constructor
	@Test
	public void parameterizedConstructorWorks() {
		Post postWithContent = new Post("Test content", testUser);
		assertThat(postWithContent.getContent(), containsString("Test content"));
		assertThat(postWithContent.getUser(), is(testUser));
		assertNotNull(postWithContent.getCreatedAt()); // Ensure the createdAt timestamp is set
	}

	// 3. Test getter for content
	@Test
	public void postHasContent() {
		assertThat(post.getContent(), containsString("hello"));
	}

	// 4. Test setter for content
	@Test
	public void canSetContent() {
		post.setContent("Updated content");
		assertThat(post.getContent(), containsString("Updated content"));
	}

	// 5. Test getter for user
	@Test
	public void postHasUser() {
		assertThat(post.getUser().getUsername(), containsString("test@gmail.com"));
	}

	// 6. Test setter for user
	@Test
	public void canSetUser() {
		User newUser = new User("newuser@gmail.com");
		post.setUser(newUser);
		assertThat(post.getUser().getUsername(), containsString("newuser@gmail.com"));
	}

	// 7. Test getter for createdAt
	@Test
	public void postHasCreatedAt() {
		assertNotNull(post.getCreatedAt());
	}

	// 8. Test setter for createdAt
	@Test
	public void canSetCreatedAt() {
		LocalDateTime newCreatedAt = LocalDateTime.now().minusDays(1);
		post.setCreatedAt(newCreatedAt);
		assertThat(post.getCreatedAt(), is(newCreatedAt));
	}

	// 9. Test getter for id
	@Test
	public void postHasId() {
		post.setId(1L); // Manually set the ID for testing
		assertThat(post.getId(), is(1L));
	}

	// 10. Test setter for id
	@Test
	public void canSetId() {
		post.setId(2L);
		assertThat(post.getId(), is(2L));
	}

	// 11. Test adding comments to a post
	@Test
	public void canAddCommentsToPost() {
		Comment comment1 = new Comment("Comment 1", post, testUser);
		Comment comment2 = new Comment("Comment 2", post, testUser);

		List<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);

		post.setComments(comments);
		assertThat(post.getComments().size(), is(2));
	}

	// 12. Test setting likes
	@Test
	public void canSetLikesForPost() {
		Like like1 = new Like(testUser, post);
		Like like2 = new Like(new User("user2@gmail.com"), post);

		List<Like> likes = new ArrayList<>();
		likes.add(like1);
		likes.add(like2);

		post.setLikes(likes);
		assertThat(post.getLikeCount(), is(2)); // Should return 2 likes
	}

	// 13. Test isLikedBy() method
	@Test
	public void testIsLikedBy() {
		Like like1 = new Like(testUser, post);
		post.setLikes(Arrays.asList(like1)); // Set a single like by testUser

		assertThat(post.isLikedBy(testUser.getId()), is(true)); // Post is liked by testUser
		assertThat(post.isLikedBy(999L), is(false)); // Post is not liked by a random user
	}

	// 14. Test getLikeCount() method
	@Test
	public void postHasCorrectLikeCount() {
		Like like1 = new Like(testUser, post);
		Like like2 = new Like(new User("user2@gmail.com"), post);

		post.setLikes(Arrays.asList(like1, like2));
		assertThat(post.getLikeCount(), is(2)); // Should return 2 likes
	}

	// 15. Test posts are in descending order of creation time
	@Test
	public void postsAreInDescendingOrder() {
		// Manually setting creation times for both posts for testing purposes
		post1.setCreatedAt(LocalDateTime.now().minusHours(2)); // Created 2 hours ago
		post2.setCreatedAt(LocalDateTime.now().minusHours(1)); // Created 1 hour ago (more recent)
		// Add posts to a list in reverse order to simulate fetching from the database
		List<Post> posts = Arrays.asList(post1, post2);

		// Sort the list manually to simulate sorting as it would happen in the repository
		posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

		// Verify that the posts are ordered by createdAt in descending order
		assertThat(posts.get(0).getCreatedAt().isAfter(posts.get(1).getCreatedAt()), is(true));
	}
}
