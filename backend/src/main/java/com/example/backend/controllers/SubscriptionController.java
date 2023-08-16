package com.example.backend.controllers;

import com.example.backend.models.Subscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/subscription")
@Tag(name = "Subscription controller", description = "API to handle subscription-related operations")
public interface SubscriptionController {

    @PostMapping("/subscribe/{userIdToSubscribe}")
    @Operation(summary = "Subscribe to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully subscribed to user"),
            @ApiResponse(responseCode = "400", description = "Subscription unsuccessful")
    })
    ResponseEntity<Subscription> subscribeToUser(@PathVariable Long userIdToSubscribe);
}
