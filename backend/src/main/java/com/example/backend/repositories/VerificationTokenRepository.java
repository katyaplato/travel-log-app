package com.example.backend.repositories;

import com.example.backend.models.VerificationToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository {

    Optional<VerificationToken> findByVerificationToken(String token);
    void save(VerificationToken verificationToken);
    void delete(VerificationToken oldToken);

}
