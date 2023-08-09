package com.example.backend.services;

import com.example.backend.models.VerificationToken;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;


public interface EmailService {
    void send(String recipient, String nickName, String token);

    VerificationToken createVerificationToken();

    void regenerateVerificationToken(String token) throws ChangeSetPersister.NotFoundException;
}
