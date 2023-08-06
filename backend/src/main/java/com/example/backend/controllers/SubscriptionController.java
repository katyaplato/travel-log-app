package com.example.backend.controllers;

import com.example.backend.models.Subscription;
import com.example.backend.repositories.SubscriptionRepository;
import com.example.backend.services.SubscriptionService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController (SubscriptionRepository subscriptionRepository, SubscriptionService subscriptionService){
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;
    }
    @PostMapping("/subscribe/{userIdToSubscribe}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Subscription> subscribeToUser(@PathVariable Long userIdToSubscribe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Subscription subscription = subscriptionService.subscribeUser(loggedInUsername, userIdToSubscribe);

        if (subscription != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(subscription);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
}
