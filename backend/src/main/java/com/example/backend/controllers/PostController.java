package com.example.backend.controllers;

import com.example.backend.models.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts")
@Tag(name = "Post controller", description = "API to handle post-related operations")
public interface PostController {

    @PostMapping("/create")
    @Operation(summary = "Create a new post")
    @ApiResponse(responseCode = "201", description = "Post successfully created")
    void createPost(Post post);

    @GetMapping("/{id}")
    @Operation(summary = "Get a post by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved post by ID")
    Post getPost(@PathVariable Long id);

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a post by ID")
    @ApiResponse(responseCode = "204", description = "Post successfully deleted")
    void deletePost(@PathVariable Long id);

    @GetMapping("/{location}")
    @Operation(summary = "Get posts by location")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by location")
    Iterable<Post> getPostsByLocation(@PathVariable String location);

    @PutMapping("/{id}")
    @Operation(summary = "Update a post's description by ID")
    @ApiResponse(responseCode = "200", description = "Post description successfully updated")
    Post updatePostDescription(@PathVariable Long id, String newDescription);
}

