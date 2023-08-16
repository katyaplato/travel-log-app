package com.example.backend.controllers;

import com.example.backend.models.Subscription;
import com.example.backend.repositories.SubscriptionRepository;
import com.example.backend.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubscriptionControllerImpl implements SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Override
    public ResponseEntity<Subscription> subscribeToUser(@PathVariable Long userIdToSubscribe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Subscription subscription = subscriptionService.subscribeUser(loggedInUsername, userIdToSubscribe);

        if (subscription != null) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
