package com.example.backend.controllers;

import com.example.backend.dtos.LoginRequest;
import com.example.backend.dtos.RegisterRequest;
import com.example.backend.services.AuthenticationService;
import com.example.backend.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authService;
    private final EmailService emailService;

    @Override
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @Override
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authService.refreshAccessToken(request));
    }

    @Override
    public ResponseEntity<?> verifyEmail(@RequestParam String token) throws ChangeSetPersister.NotFoundException {
        authService.verifyUser(token);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> newVerifyEmail(@RequestParam String token) throws ChangeSetPersister.NotFoundException {
        emailService.regenerateVerificationToken(token);
        return ResponseEntity.ok().build();
    }
}