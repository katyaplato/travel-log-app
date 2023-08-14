package com.example.backend.controllers;

import com.example.backend.models.Post;
import com.example.backend.models.User;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequestMapping("api//posts")
@RestController
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;
    private final UserRepository userRepository;

    @Autowired
    public PostController(PostRepository postRepository, PostService postService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postService = postService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    @SecurityRequirement(name = "Bearer Authentication")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody Post post) {
        postService.validateNewPost(post);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Optional<User> optionalUser = userRepository.findByEmail(loggedInUsername);
        if (optionalUser.isEmpty()) {
            throw new Error("User not found or not authenticated.");
        }
        User user = optionalUser.get();

        post.setUser(user);
        user.getPosts().add(post);
        postRepository.save(post);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return post.get();
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Post postToDelete = optionalPost.get();
        postRepository.delete(postToDelete);
    }

    @GetMapping("/{location}")
    public Iterable<Post> getPostsByLocation(@PathVariable("location") String location) {
        if (location.isEmpty()) {
            throw new Error("Please, enter a location");
        }
        return postRepository.findByLocation(location);
    }

    @PutMapping("/{id}")
    public Post updatePostDescription(@PathVariable Long id, String newDescription) {
        if (newDescription.isEmpty()) {
            throw new Error("Please, enter a description.");
        }
        return postService.updateDescription(id, newDescription);
    }
}

