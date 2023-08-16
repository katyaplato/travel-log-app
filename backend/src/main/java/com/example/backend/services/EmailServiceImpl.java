package com.example.backend.services;

import com.example.backend.models.User;
import com.example.backend.models.VerificationToken;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;


    private String username;

    private String link;

    @Override
    public void send(String recipient, String nickName, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(recipient);
        mailMessage.setSubject("Travel App Email Verification");
        mailMessage.setText("Hello " + nickName + "\n" +
                "Please verify your email by clicking the link below:\n" +
                link + "/verification/" + token + "\n" +

                "This link will expire in 15 minutes.");
        javaMailSender.send(mailMessage);
    }

    @Override
    public VerificationToken createVerificationToken() {
        VerificationToken verificationToken = VerificationToken.builder()
                .verificationToken(UUID.randomUUID().toString())
                .verificationTokenExpiration(LocalDateTime.now().plusMinutes(15))
                .build();
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }



    @Override
    public void regenerateVerificationToken(String originalToken) throws ChangeSetPersister.NotFoundException {
        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByVerificationToken(originalToken);
        if (optionalVerificationToken.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        VerificationToken oldToken = optionalVerificationToken.get();

        User user = oldToken.getUser();
        if (user == null) {
            throw new ChangeSetPersister.NotFoundException();
        }

        VerificationToken newToken = createVerificationToken();

        user.setVerificationToken(newToken);
        userRepository.save(user);

        verificationTokenRepository.delete(oldToken);

        send(user.getEmail(), user.getUsername(), newToken.getVerificationToken());
    }

    @Override
    public void sendUploadNotificationToSubscribers(String subscriberEmail, String subscribedUserName, String uploadDetails) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(subscriberEmail);
        mailMessage.setSubject("New Upload Notification");
        mailMessage.setText("Hello,\n\n" +
                "A new upload has been added by " + subscribedUserName + ":\n\n" +
                uploadDetails + "\n\n" +
                "You can view the upload at: " + link + "\n\n" +
                "Thank you for subscribing!");

        javaMailSender.send(mailMessage);
    }
}
