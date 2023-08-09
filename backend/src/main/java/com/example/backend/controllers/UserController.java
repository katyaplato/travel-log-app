package com.example.backend.controllers;

import com.example.backend.dtos.UserGetInfoDTO;
import com.example.backend.models.Post;
import com.example.backend.models.Subscription;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.PostService;
import com.example.backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    public final UserRepository userRepository;
    public final UserService userService;
    public final PostRepository postRepository;
    public final PostService postService;



    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> getAllPostsByUser(@PathVariable Long id) {
                List<Post> posts = userService.getAllPosts(id);

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<List<Subscription>> getAllSubscriptions(@PathVariable Long id) {
        List<Subscription> subscriptions = userService.getAllSubscriptions(id);

        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/{id}")
    public UserGetInfoDTO getUserInfo(@PathVariable Long id){
        return this.userService.getUserInfo(id);
    }
}

//  PUT /api/users/{userId}: Update user profile by user ID.




