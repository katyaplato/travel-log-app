package com.example.backend.controllers;

import com.example.backend.repositories.SubscriptionRepository;
import com.example.backend.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
