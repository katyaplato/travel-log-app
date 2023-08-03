package com.example.backend.repositories;

import com.example.backend.models.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository <Subscription,Long> {
}
