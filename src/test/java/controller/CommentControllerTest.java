package controller;

import com.makersacademy.acebook.model.Comment;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.CommentRepository;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import com.makersacademy.acebook.controller.CommentController;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentControllerTest {

    @Mock
    private HttpSession session;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);}

    @Test
    void testUserCanCreateNewComment(){
        User user1 = new User("user1@example.com");
        user1.setId(1L);
        User user2 = new User("user2@example.com");
        user2.setId(2L);
        Post post = new Post("example post", user1, false);
        post.setId(1L);
        String content = "test content";

        when(session.getAttribute("user_id")).thenReturn(user2.getId());
        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        ResponseEntity<String> result = commentController.createPost(content, post.getId(), session);

        assertEquals(ResponseEntity.ok("Created successfully"), result);
        verify(commentRepository).save(new Comment("test content", post, user2));

    }

    @Test
    void testUserCanDeleteComment(){
        User user = new User("user@example.com");
        user.setId(1L);
        Post post = new Post("example post", user, false);
        post.setId(1L);
        Comment comment = new Comment("example comment", post, user);
        comment.setId(1L);

        when(session.getAttribute("user_id")).thenReturn(user.getId());
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        ResponseEntity<String> result = commentController.deleteComment(comment.getId(), session);
        assertEquals(ResponseEntity.ok("Deleted successfully"), result);
        verify(commentRepository).delete(comment);
    }

    @Test
    void testUserCanEditComment(){
        User user = new User("user@example.com");
        user.setId(1L);
        Post post = new Post("example post", user, false);
        post.setId(1L);
        Comment comment = new Comment("example comment", post, user);
        comment.setId(1L);
        String new_content = "updated comment";

        when(session.getAttribute("user_id")).thenReturn(user.getId());
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        ResponseEntity<String> result = commentController.updateComment(comment.getId(), new_content, session);

        assertEquals(ResponseEntity.ok("Comment updated successfully"), result);
        comment.setContent(new_content);
        verify(commentRepository).save(comment);
    }
}
