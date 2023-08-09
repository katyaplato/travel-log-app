package com.example.backend.services;

import com.example.backend.dtos.AuthenticationResponse;
import com.example.backend.dtos.LoginRequest;
import com.example.backend.dtos.RegisterRequest;
import com.example.backend.dtos.RegistrationDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

public interface AuthenticationService {
     RegistrationDTO register(RegisterRequest request);

     AuthenticationResponse login(LoginRequest request);

     AuthenticationResponse refreshAccessToken(HttpServletRequest request);

     void verifyUser(String token) throws ChangeSetPersister.NotFoundException;
}
