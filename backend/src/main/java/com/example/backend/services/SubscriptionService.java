package com.example.backend.services;

import com.example.backend.models.Subscription;
import com.example.backend.models.User;
import com.example.backend.repositories.SubscriptionRepository;
import com.example.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriptionService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;


    public Subscription subscribeUser(String loggedInUsername, Long userIdToSubscribe) {
        Optional<User> optionalUser = userRepository.findByUsername(loggedInUsername);
        if(optionalUser.isEmpty()){
            throw new Error("User not found.");
        }
        User subscriber = optionalUser.get();

        Optional<User> optionalUser2 = userRepository.findById(userIdToSubscribe);
        if(optionalUser2.isEmpty()){
            throw new Error("User not found.");
        }
        User userToSubscribe = optionalUser2.get();

        if (subscriber.getId().equals(userIdToSubscribe)) {
            return null; // Return null if trying to subscribe to oneself
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
