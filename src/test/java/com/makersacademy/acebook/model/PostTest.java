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
	private User testUser = new User("test@gmail.com");
	private Post post1 = new Post("First post", testUser, false);
	private Post post2 = new Post("Second post", testUser, false);
	private Post post = new Post("hello", testUser, false);

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

	// New test to check the Post constructor and getter for content
	@Test
	public void testPostConstructorAndGetContent() {
		Post newPost = new Post("Hello World", testUser);
		assertThat(newPost.getContent(), is("Hello World"));
		assertThat(newPost.getUser(), is(testUser));
		assertThat(newPost.getIs_image(), is(false)); // Default image flag
	}

	// New test to check the setter and getter for id
	@Test
	public void testPostIdSetterAndGetter() {
		Post newPost = new Post();
		newPost.setId(1L);
		assertThat(newPost.getId(), is(1L));
	}

	// New test to check the setter and getter for content
	@Test
	public void testPostContentSetterAndGetter() {
		Post newPost = new Post();
		newPost.setContent("Updated Content");
		assertThat(newPost.getContent(), is("Updated Content"));
	}

	// New test to check the setter and getter for user
	@Test
	public void testPostUserSetterAndGetter() {
		Post newPost = new Post();
		newPost.setUser(testUser);
		assertThat(newPost.getUser(), is(testUser));
	}

	// New test to check the setter and getter for createdAt
	@Test
	public void testPostCreatedAtSetterAndGetter() {
		Post newPost = new Post();
		LocalDateTime now = LocalDateTime.now();
		newPost.setCreatedAt(now);
		assertThat(newPost.getCreatedAt(), is(now));
	}

	// New test to check the setter and getter for comments
	@Test
	public void testPostCommentsSetterAndGetter() {
		Post newPost = new Post();
		List<Comment> comments = Arrays.asList(new Comment(), new Comment());
		newPost.setComments(comments);
		assertThat(newPost.getComments(), is(comments));
	}

	// New test to check the setter and getter for likes
	@Test
	public void testPostLikesSetterAndGetter() {
		Post newPost = new Post();
		List<Like> likes = Arrays.asList(new Like(), new Like());
		newPost.setLikes(likes);
		assertThat(newPost.getLikes(), is(likes));
	}

	// New test to check the setter and getter for image flag
	@Test
	public void testPostIsImageSetterAndGetter() {
		Post newPost = new Post();
		newPost.setIs_image(true);
		assertThat(newPost.getIs_image(), is(true));
	}
}
