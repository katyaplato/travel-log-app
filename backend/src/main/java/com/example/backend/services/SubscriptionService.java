package com.example.backend.services;

import com.example.backend.models.Subscription;

public interface SubscriptionService {
    Subscription subscribeUser(String loggedInUsername, Long userIdToSubscribe);
}

