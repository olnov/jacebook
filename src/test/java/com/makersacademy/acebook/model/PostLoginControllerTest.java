package com.makersacademy.acebook.model;


import com.makersacademy.acebook.controller.PostLoginController;
import com.makersacademy.acebook.model.User;
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

public class PostLoginControllerTest {
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
        assertEquals("redirect:/posts", result);  // Check the redirect
        verify(session).setAttribute("user_id", userId);  // Verify session attribute was set
    }

    @Test
    void handlePostLogin_UserNotInDb_ShouldThrowRuntimeException() {
        // Arrange
        String email = "nonexistent@example.com";

        // Mock behavior
        when(authentication.getPrincipal()).thenReturn(oidcUser);
        when(oidcUser.getAttribute("email")).thenReturn(email);
        when(userRepository.findByUsername(email)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            postLoginController.handlePostLogin(authentication, session);
        });

        assertEquals("User not found in the local database.", exception.getMessage());
    }
}
