package com.example.backend.repositories;

import com.example.backend.models.Subscription;
import com.example.backend.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository <Subscription,Long> {
    Subscription findBySubscriberAndUserToSubscribe(User subscriber, User userToSubscribe);
    List<Subscription> findSubscriptionsByUserToSubscribe(User subscriber);
}
