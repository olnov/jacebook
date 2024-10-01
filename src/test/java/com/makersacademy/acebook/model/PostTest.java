package com.makersacademy.acebook.model;

// Importing static methods from Hamcrest for matchers and assertions
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test; // Importing the JUnit 5 Test annotation

import java.time.LocalDateTime; // Importing LocalDateTime for time-related operations
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PostTest {

	// Initialising post objects with different content
	private User testUser = new User("Test User");
	private Post post1 = new Post("First post", testUser);
	private Post post2 = new Post("Second post", testUser);
	private Post post = new Post("hello", testUser);

	// Test to check if the content of the post contains the word "hello"
	@Test
	public void postHasContent() {
		// Verifying that the content of the post contains the string "hello"
		assertThat(post.getContent(), containsString("hello"));
	}

	// Test to check if posts are in descending order of creation time
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







