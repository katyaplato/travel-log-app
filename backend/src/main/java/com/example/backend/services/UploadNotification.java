package com.example.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UploadNotificationTask {

    private final EmailService emailService;
    private final UploadService uploadService; // You need to define your UploadService

    @Autowired
    public UploadNotificationTask(EmailService emailService, UploadService uploadService) {
        this.emailService = emailService;
        this.uploadService = uploadService;
    }

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

