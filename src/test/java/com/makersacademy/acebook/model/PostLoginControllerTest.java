package com.makersacademy.acebook.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;

public class PostLoginControllerTest {

    @Test
    public void testUserDefaultConstructor() {
        User user = new User();
        assertThat(user.isEnabled(), is(true)); // Check if the user is enabled by default
        assertThat(user.getUsername(), is(nullValue())); // Ensure username is null
    }

    @Test
    public void testUserConstructorWithUsername() {
        String username = "testUser";
        User user = new User(username);
        assertThat(user.getUsername(), is(username)); // Check if username is set correctly
        assertThat(user.isEnabled(), is(true)); // Check if enabled is set to true by default
    }

    @Test
    public void testUserConstructorWithEnabledFlag() {
        String username = "testUser";
        boolean enabled = false;
        User user = new User(username, enabled);
        assertThat(user.getUsername(), is(username)); // Check if username is set correctly
        assertThat(user.isEnabled(), is(enabled)); // Check if enabled is set to false
    }

    @Test
    public void testUserConstructorWithFullNameAndBio() {
        String fullName = "Test User";
        String bio = "This is a test bio.";
        User user = new User(fullName, bio);
        assertThat(user.getFull_name(), is(fullName)); // Check if full name is set correctly
        assertThat(user.getBio(), is(bio)); // Check if bio is set correctly
    }

    @Test
    public void testUserConstructorWithAllFields() {
        String username = "testUser";
        boolean enabled = true;
        String fullName = "Test User";
        String bio = "This is a test bio.";
        String userPhoto = "photo_url";

        User user = new User(username, enabled, fullName, bio, userPhoto);

        assertThat(user.getUsername(), is(username));
        assertThat(user.isEnabled(), is(enabled));
        assertThat(user.getFull_name(), is(fullName));
        assertThat(user.getBio(), is(bio));
        assertThat(user.getUser_photo(), is(userPhoto));
    }

    @Test
    public void testSettersAndGetters() {
        User user = new User();

        // Test setting and getting username
        String username = "newUser";
        user.setUsername(username);
        assertThat(user.getUsername(), is(username));

        // Test setting and getting full name
        String fullName = "New User";
        user.setFull_name(fullName);
        assertThat(user.getFull_name(), is(fullName));

        // Test setting and getting bio
        String bio = "This is my bio.";
        user.setBio(bio);
        assertThat(user.getBio(), is(bio));

        // Test setting and getting user photo
        String userPhoto = "new_photo_url";
        user.setUser_photo(userPhoto);
        assertThat(user.getUser_photo(), is(userPhoto));

        // Test setting and getting enabled flag
        user.setEnabled(false);
        assertThat(user.isEnabled(), is(false));
    }
}
