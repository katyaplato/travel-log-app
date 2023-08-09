package com.example.backend.repositories;

import com.example.backend.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository  extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByVerificationToken(String token);
//    void save(VerificationToken verificationToken);
//    void delete(VerificationToken oldToken);

}
