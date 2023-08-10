package com.example.backend;

import com.example.backend.models.Post;
import com.example.backend.models.User;
import com.example.backend.controllers.PostController;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PostController.class)
class CreatePostUnitTest {

    @Mock
    private PostService postService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCreatePost() {

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("testuser")
                .password("password")
                .build();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(userDetails.getUsername());


        User user = new User();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));


        Post post = new Post();
        doNothing().when(postService).validateNewPost(post);
        when(postRepository.save(post)).thenReturn(post);


        postController.createPost(post);


        verify(postService).validateNewPost(post);
        verify(userRepository).findByUsername(userDetails.getUsername());
        verify(postRepository).save(post);
        verify(user).getPosts().add(post);
    }
}

