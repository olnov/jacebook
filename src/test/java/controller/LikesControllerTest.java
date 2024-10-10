package controller;

import com.makersacademy.acebook.controller.LikesController;
import com.makersacademy.acebook.model.Like;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.LikeRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikesControllerTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LikesController likesController;

    private User mockUser;
    private Post mockPost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks

        mockUser = new User();
        mockUser.setId(1L);  // Set a mock user ID

        mockPost = new Post();
        mockPost.setId(1L);  // Set a mock post ID
    }

    @Test
    void testCreateLike_Successful() {
        // Arrange
        Long postId = 1L;
        Long userId = 1L;

        when(session.getAttribute("user_id")).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        when(likeRepository.findByUserIdAndPostId(userId, postId)).thenReturn(Optional.empty());  // No like exists

        // Act
        RedirectView result = likesController.createLike(postId, session);

        // Assert
        assertEquals("/", result.getUrl());  // The redirect URL should be "/"
        verify(likeRepository, times(1)).save(any(Like.class));  // The like should be saved once
    }

    @Test
    void testCreateLike_AlreadyLiked() {
        // Arrange
        Long postId = 1L;
        Long userId = 1L;

        Like existingLike = new Like(mockUser, mockPost);  // A like already exists

        when(session.getAttribute("user_id")).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        when(likeRepository.findByUserIdAndPostId(userId, postId)).thenReturn(Optional.of(existingLike));  // Like exists

        // Act
        RedirectView result = likesController.createLike(postId, session);

        // Assert
        assertEquals("/", result.getUrl());  // The redirect URL should still be "/"
        verify(likeRepository, never()).save(any(Like.class));  // No new like should be saved
    }

    @Test
    void testCreateLike_UserNotFound() {
        // Arrange
        Long postId = 1L;
        Long userId = 1L;

        when(session.getAttribute("user_id")).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());  // User not found

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            likesController.createLike(postId, session);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testCreateLike_PostNotFound() {
        // Arrange
        Long postId = 1L;
        Long userId = 1L;

        when(session.getAttribute("user_id")).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());  // Post not found

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            likesController.createLike(postId, session);
        });
        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void testDeleteLike_Successful() {
        // Arrange
        Long postId = 1L;
        Long userId = 1L;

        Like existingLike = new Like(mockUser, mockPost);  // Assume a like already exists

        when(session.getAttribute("user_id")).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        when(likeRepository.findByUserIdAndPostId(userId, postId)).thenReturn(Optional.of(existingLike));

        // Act
        ResponseEntity result = likesController.deleteLike(postId, session);

        // Assert
        assertEquals(200, result.getStatusCodeValue());  // Should return 200 OK
        verify(likeRepository, times(1)).delete(existingLike);  // Like should be deleted once
    }

    @Test
    void testDeleteLike_LikeNotFound() {
        // Arrange
        Long postId = 1L;
        Long userId = 1L;

        when(session.getAttribute("user_id")).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        when(likeRepository.findByUserIdAndPostId(userId, postId)).thenReturn(Optional.empty());  // Like not found

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            likesController.deleteLike(postId, session);
        });
        assertEquals("like not found", exception.getMessage());
    }
}
