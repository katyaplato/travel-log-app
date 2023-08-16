package com.example.backend.controllers;

import com.example.backend.models.Post;
import com.example.backend.models.Subscription;
import com.example.backend.models.User;
import com.example.backend.repositories.PostRepository;
import com.example.backend.repositories.SubscriptionRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.EmailService;
import com.example.backend.services.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
public class PostControllerImpl implements PostController {
    private final PostRepository postRepository;
    private final PostService postService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final SubscriptionRepository subscriptionRepository;

    @SecurityRequirement(name = "Bearer Authentication")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public void createPost(@RequestBody Post post) {
        postService.validateNewPost(post);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Optional<User> optionalUser = userRepository.findByEmail(loggedInUsername);
        if (optionalUser.isEmpty()) {
            throw new Error("User not found or not authenticated.");
        }
        User user = optionalUser.get();

        post.setUser(user);
        user.getPosts().add(post);
        postRepository.save(post);

        List<Subscription> subscriptionList = subscriptionRepository.findSubscriptionsByUserToSubscribe(user);
        for (Subscription subscription : subscriptionList) {
            String subscriberEmail = subscription.getSubscriber().getEmail();
            String link = "link";
            String subscriberName = subscription.getSubscriber().getFullName();
            String emailText = "Hello, " + subscriberName +
                    "A new upload has been added by " + user.getFullName() + ":\n\n" +
                    "\n\n" +
                    "You can view the upload at: " + link + "\n\n" +
                    "Thank you for subscribing!";
            emailService.sendEmail(subscriberEmail, "Travel App", user.getFullName() + "'s upload", emailText);
        }
    }


    @Override
    public Post getPost(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return post.get();
    }

    @DeleteMapping("/{id}")
    @Override
    public void deletePost(@PathVariable Long id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Post postToDelete = optionalPost.get();
        postRepository.delete(postToDelete);
    }


    @Override
    public Iterable<Post> getPostsByLocation(@PathVariable("location") String location) {
        if (location.isEmpty()) {
            throw new Error("Please, enter a location");
        }
        return postRepository.findByLocation(location);
    }


    @Override
    public Post updatePostDescription(@PathVariable Long id, String newDescription) {
        if (newDescription.isEmpty()) {
            throw new Error("Please, enter a description.");
        }
        return postService.updateDescription(id, newDescription);
    }
}

