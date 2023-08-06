package com.example.backend.services;

import com.example.backend.models.Subscription;
import com.example.backend.models.User;
import com.example.backend.repositories.SubscriptionRepository;
import com.example.backend.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription subscribeUser(String loggedInUsername, Long userIdToSubscribe) {
        User subscriber = userRepository.findUserByUsername(loggedInUsername);
        User userToSubscribe = userRepository.findById(userIdToSubscribe).orElse(null);

        if (subscriber == null || userToSubscribe == null || subscriber.getId().equals(userIdToSubscribe)) {
            return null; // Return null if any of the users does not exist or if trying to subscribe to oneself
        }

        Subscription existingSubscription = subscriptionRepository.findBySubscriberAndUserToSubscribe(subscriber, userToSubscribe);
        if (existingSubscription != null) {
            return null; // Return null if already subscribed
        }
        Subscription newSubscription = new Subscription();
        newSubscription.setSubscriber(subscriber);
        newSubscription.setUserToSubscribe(userToSubscribe);
        newSubscription.setSubscriptionDate(LocalDate.now());

        return subscriptionRepository.save(newSubscription);
    }
}
