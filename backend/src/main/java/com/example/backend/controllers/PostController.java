package com.example.backend.controllers;

import com.example.backend.models.Post;
import com.example.backend.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("post")
@RestController
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    public PostController (PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost (@RequestBody Post post) {
     return null;
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
