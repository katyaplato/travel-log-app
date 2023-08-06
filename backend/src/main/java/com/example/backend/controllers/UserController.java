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
import org.springframework.ui.Model;
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

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Validated User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }
//    @PutMapping("/{userId}")
//    public ResponseEntity<User> updateUserProfile(@PathVariable Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
//        User existingUser = userRepository.findById(userId).orElse(null);
//        if (existingUser == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        User updatedUser = userService.updateUserProfile(existingUser, userUpdateDTO);
//
//        return ResponseEntity.ok(updatedUser);
//    }
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




