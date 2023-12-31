package com.example.backend.services;

import com.example.backend.models.Subscription;
import com.example.backend.models.User;
import com.example.backend.repositories.SubscriptionRepository;
import com.example.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription subscribeUser(String loggedInUsername, Long userIdToSubscribe) {
        Optional<User> optionalUser = userRepository.findByEmail(loggedInUsername);
        if (optionalUser.isEmpty()) {
            throw new Error("User not found.");
        }
        User subscriber = optionalUser.get();

        Optional<User> optionalUser2 = userRepository.findById(userIdToSubscribe);
        if (optionalUser2.isEmpty()) {
            throw new Error("User not found.");
        }
        User userToSubscribe = optionalUser2.get();

        if (subscriber.getId().equals(userIdToSubscribe)) {
            return null;
        }

        Subscription existingSubscription = subscriptionRepository.findBySubscriberAndUserToSubscribe(subscriber, userToSubscribe);
        if (existingSubscription != null) {
            return null;
        }
        Subscription newSubscription = new Subscription();
        newSubscription.setSubscriber(subscriber);
        newSubscription.setUserToSubscribe(userToSubscribe);
        newSubscription.setSubscriptionDate(LocalDate.now());

        return subscriptionRepository.save(newSubscription);
    }
}
