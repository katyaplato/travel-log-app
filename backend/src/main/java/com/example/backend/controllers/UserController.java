package com.example.backend.controllers;

import com.example.backend.dtos.UserGetInfoDTO;
import com.example.backend.models.Post;
import com.example.backend.models.Subscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/users")
@Tag(name = "User controller", description = "API to handle user-related operations")
public interface UserController {

    @GetMapping("/{id}/posts")
    @Operation(summary = "Get all posts by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by user ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<List<Post>> getAllPostsByUser(@PathVariable Long id);

    @GetMapping("/{id}/subscriptions")
    @Operation(summary = "Get all subscriptions by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscriptions by user ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<List<Subscription>> getAllSubscriptions(@PathVariable Long id);

    @GetMapping("/{id}")
    @Operation(summary = "Get user info by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user info by user ID"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    UserGetInfoDTO getUserInfo(@PathVariable Long id);
}
