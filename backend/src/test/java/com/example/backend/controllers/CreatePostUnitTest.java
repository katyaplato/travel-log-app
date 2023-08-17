package com.example.backend.controllers;

import com.example.backend.models.Post;
import com.example.backend.models.User;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.PostServiceImpl;
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

@SpringBootTest(classes = PostControllerImpl.class)
class CreatePostUnitTest {

    @Mock
    private PostServiceImpl postServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private PostControllerImpl postControllerImpl;

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
        doNothing().when(postServiceImpl).validateNewPost(post);
        when(postRepository.save(post)).thenReturn(post);


        postControllerImpl.createPost(post);


        verify(postServiceImpl).validateNewPost(post);
        verify(userRepository).findByUsername(userDetails.getUsername());
        verify(postRepository).save(post);
        verify(user).getPosts().add(post);
    }
}

