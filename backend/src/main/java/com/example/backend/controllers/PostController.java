package com.example.backend.controllers;

import com.example.backend.models.Post;
import com.example.backend.repositories.PostRepository;
import com.example.backend.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequestMapping("/posts")
@RestController
public class PostController {
    private PostRepository postRepository;
    private PostService postService;

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

    @GetMapping("/{id}")
    public Post getPost (@PathVariable Long id){
    Optional <Post> post = postRepository.findById(id);
    if(post.isEmpty()){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    } return post.get();
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable ("id") Long id){
        Optional<Post> optionalPost = this.postRepository.findById(id);
        if(optionalPost.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } Post postToDelete = optionalPost.get();
        postRepository.delete(postToDelete);

    }
}


//	Post Endpoints:
//        •	PUT /api/posts/{postId}: Update a post by post ID.
//        •	DELETE /api/posts/{postId}: Delete a post by post ID.
//        •	GET /api/posts: Get all posts (maybe with optional filters like location or tags).
//        •	GET /api/posts/{postId}/images: Get all images associated with a post.
//        •	POST /api/posts/{postId}/images: Upload an image for a post.
