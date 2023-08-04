package com.example.backend.controllers;

import com.example.backend.dtos.UserRegistrationDTO;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    public final UserRepository userRepository;
    public final UserService userService;

    @Autowired
    public UserController (UserRepository userRepository, UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {

        User registeredUser = userService.registerUser(userRegistrationDTO);

        if (registeredUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

// POST /api/register: Register a new user.
//•	POST /api/login: User login.
//  GET /api/users/{userId}: Get user details by user ID.
//  PUT /api/users/{userId}: Update user profile by user ID.
//  GET /api/users/{userId}/posts: Get all posts created by a specific user.
//  GET /api/users/{userId}/subscriptions: Get all subscriptions of a specific user.
//•	GET /api/users/{userId}/subscribers: Get all subscribers of a specific user.

}

