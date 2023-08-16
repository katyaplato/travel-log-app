package com.example.backend.services;

import com.example.backend.models.Upload;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UploadNotification {

    private final EmailService emailService;
    private final UploadService uploadService; // You need to define your UploadService
    private final SubscriptionService subscriptionService;


    @Scheduled(fixedRate = 60000) // Check for new uploads every minute
    public void sendUploadNotifications() {
        // Retrieve new uploads using your UploadService
        List<Upload> newUploads = uploadService.getNewUploads();

        for (Upload upload : newUploads) {
            String subscribedUserName = upload.getUser().getUsername(); // Assuming you have a User associated with the upload
            List<String> subscriberEmails = subscriptionService.getSubscriberEmailsForUser(subscribedUserName);

            for (String subscriberEmail : subscriberEmails) {
                // Construct upload details (e.g., title, description)
                String uploadDetails = "New Upload: " + upload.getTitle() + "\nDescription: " + upload.getDescription();

                // Send upload notification to each subscriber's email
                emailService.sendUploadNotificationToSubscribers(subscriberEmail, subscribedUserName, uploadDetails);
            }
        }
    }
}

