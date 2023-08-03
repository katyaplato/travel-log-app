package com.example.backend.controllers;

import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    public final UserRepository userRepository;

    @Autowired
    public UserController (UserRepository userRepository){
        this.userRepository = userRepository;
    }

// POST /api/register: Register a new user.
//•	POST /api/login: User login.
//  GET /api/users/{userId}: Get user details by user ID.
//  PUT /api/users/{userId}: Update user profile by user ID.
//  GET /api/users/{userId}/posts: Get all posts created by a specific user.
//  GET /api/users/{userId}/subscriptions: Get all subscriptions of a specific user.
//•	GET /api/users/{userId}/subscribers: Get all subscribers of a specific user.

}
