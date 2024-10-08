package com.makersacademy.acebook.model;

import com.makersacademy.acebook.controller.PostLoginController;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostLoginControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpSession session;

    @Mock
    private Authentication authentication;

    @Mock
    private OidcUser oidcUser;

    @InjectMocks
    private PostLoginController postLoginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void handlePostLogin_UserExistsInDb_ShouldStoreUserIdInSessionAndRedirectToPosts() {
        // Arrange
        String email = "user@example.com";
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername(email);

        // Mock behavior
        when(authentication.getPrincipal()).thenReturn(oidcUser);
        when(oidcUser.getAttribute("email")).thenReturn(email);
        when(userRepository.findByUsername(email)).thenReturn(Optional.of(user));

        // Act
        String result = postLoginController.handlePostLogin(authentication, session);

        // Assert
        assertEquals("redirect:/", result);  // Check the redirect
        verify(session).setAttribute("user_id", userId);  // Verify session attribute was set
        verify(userRepository, never()).save(any());  // Ensure no new user was saved
    }

    @Test
    void handlePostLogin_UserNotInDb_ShouldCreateNewUserAndStoreUserIdInSession() {
        // Arrange
        String email = "newuser@example.com";
        Long userId = 2L;
        User newUser = new User();
        newUser.setId(userId);
        newUser.setUsername(email);

        // Mock behavior
        when(authentication.getPrincipal()).thenReturn(oidcUser);
        when(oidcUser.getAttribute("email")).thenReturn(email);
        when(userRepository.findByUsername(email)).thenReturn(Optional.empty())  // User not found initially
                .thenReturn(Optional.of(newUser));  // Return new user after save
        when(userRepository.save(any(User.class))).thenReturn(newUser);  // Mock save operation

        // Act
        String result = postLoginController.handlePostLogin(authentication, session);

        // Assert
        assertEquals("redirect:/", result);  // Check the redirect
        verify(userRepository).save(any(User.class));  // Verify that the new user was saved
        verify(session).setAttribute("user_id", userId);  // Verify session attribute was set for the new user
    }

    @Test
    void handlePostLogin_UserNotInDb_AndCannotBeCreated_ShouldThrowRuntimeException() {
        // Arrange
        String email = "newuser@example.com";

        // Mock behavior
        when(authentication.getPrincipal()).thenReturn(oidcUser);
        when(oidcUser.getAttribute("email")).thenReturn(email);
        when(userRepository.findByUsername(email)).thenReturn(Optional.empty())  // User not found initially
                .thenReturn(Optional.empty());  // User still not found after save attempt
        when(userRepository.save(any(User.class))).thenReturn(new User(email));  // Mock save operation

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            postLoginController.handlePostLogin(authentication, session);
        });

        assertEquals("Can't create a local user.", exception.getMessage());
    }
}
