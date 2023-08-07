package com.example.backend.controllers;

import com.example.backend.dtos.UserGetInfoDTO;
import com.example.backend.dtos.UserRegistrationDTO;
import com.example.backend.models.Post;
import com.example.backend.models.Subscription;
import com.example.backend.models.User;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.PostService;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    public final UserRepository userRepository;
    public final UserService userService;
    public final PostRepository postRepository;
    public final PostService postService;

    @Autowired
    public UserController (UserRepository userRepository, UserService userService, PostRepository postRepository, PostService postService){
        this.userRepository = userRepository;
        this.userService = userService;
        this.postRepository = postRepository;
        this.postService = postService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Validated UserRegistrationDTO userRegistrationDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid registration data.");
        }

        // Check if the email is already taken
        if (userService.isEmailTaken(userRegistrationDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already taken.");
        }

        // Register the user
        // Register the user
        User registeredUser = userService.registerUser(userRegistrationDTO);

        if (registeredUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed.");
        }
    }


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
//•	POST /api/login: User login.
//  PUT /api/users/{userId}: Update user profile by user ID.




