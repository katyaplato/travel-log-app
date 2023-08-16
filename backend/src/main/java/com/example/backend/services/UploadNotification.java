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
    private final UploadService uploadService;
    private final UserService userService;


    @Scheduled(fixedRate = 60000)
    public void sendUploadNotifications() {

        List<Upload> newUploads = uploadService.getNewUploads();

        for (Upload upload : newUploads) {
            String subscribedUserName = upload.getUser().getUsername();
            List<String> subscriberEmails = userService.getSubscriberEmailsForUser(subscribedUserName);

            for (String subscriberEmail : subscriberEmails) {

                String uploadDetails = "New Upload: " + upload.getTitle() + "\nDescription: " + upload.getDescription();


               // emailService.sendEmail(subscriberEmail, subscribedUserName, uploadDetails);
            }
        }
    }
}

