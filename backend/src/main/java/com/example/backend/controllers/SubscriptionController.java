package com.example.backend.controllers;

import com.example.backend.models.Subscription;
import com.example.backend.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionController (SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
    }
}
