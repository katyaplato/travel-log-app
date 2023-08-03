package com.example.backend.controllers;

import com.example.backend.models.Post;
import com.example.backend.repositories.PostRepository;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("post")
@RestController
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    @Autowired
    public PostController (PostRepository postRepository, PostService postService){
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost (@RequestBody Post post) {
     postService.validateNewPost(post);
     postRepository.save(post);
    }
}


//	Post Endpoints:
//        •	POST /api/posts: Create a new post.
//        •	GET /api/posts/{postId}: Get post details by post ID.
//        •	PUT /api/posts/{postId}: Update a post by post ID.
//        •	DELETE /api/posts/{postId}: Delete a post by post ID.
//        •	GET /api/posts: Get all posts (maybe with optional filters like location or tags).
//        •	GET /api/posts/{postId}/images: Get all images associated with a post.
//        •	POST /api/posts/{postId}/images: Upload an image for a post.
