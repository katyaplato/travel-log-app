package com.example.backend.services;

import com.example.backend.dtos.AuthenticationResponse;
import com.example.backend.dtos.LoginRequest;
import com.example.backend.dtos.RegisterRequest;
import com.example.backend.dtos.RegistrationDTO;
import com.example.backend.models.Role;
import com.example.backend.models.User;
import com.example.backend.models.VerificationToken;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.VerificationTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public RegistrationDTO register(RegisterRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isPresent() && optionalUser.get().getEmail().equalsIgnoreCase(request.getEmail())) {
            throw new Error("User already exists.");
        }

        validateRequest(request.getPassword(), request.getEmail());

        User user = User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                //.password(request.getPassword())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .verificationToken(emailService.createVerificationToken())

                .build();

        emailService.send(user.getEmail(), user.getUsername(), user.getVerificationToken().getVerificationToken());

        userRepository.save(user);

        return RegistrationDTO
                .builder()
                .username(user.getUsername())
                .email(user.getUsername())
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {

        validateRequest(request.getPassword(), request.getEmail());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword() /*passwordEncoder.encode(request.getPassword())*/));
        } catch (Exception e) {
            throw new Error("Invalid Username or Password");
        }

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshTokenUUID(jwtService.extractTokenIdFromRefreshToken(refreshToken));
        userRepository.save(user);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void validateRequest(String password, String email) {
        if (password.isEmpty() && email.isEmpty()) {
            throw new Error("Required Password and Email");
        }

        if (password.isEmpty()) {
            throw new Error("Required Password");
        }

        if (email.isEmpty()) {
            throw new Error("Required Email");
        }
    }

    @Override
    public AuthenticationResponse refreshAccessToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new Error("Unathorized");
        }
        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUserEmailFromRefreshToken(refreshToken);

        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.extractTokenIdFromRefreshToken(refreshToken).equals(user.getRefreshTokenUUID())) {
            String newAccessToken = jwtService.generateAccessToken((UserDetails) user);
            return AuthenticationResponse.builder().accessToken(newAccessToken).build();
        } else {
            throw new Error("Unauthorized");
        }
    }

    @Override
    public void verifyUser(String token) throws ChangeSetPersister.NotFoundException {
        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByVerificationToken(token);

        if (optionalVerificationToken.isEmpty()) {
            throw new Error("Invalid Token");
        }

        VerificationToken verificationToken = optionalVerificationToken.get();
        if (verificationToken.getVerificationTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new Error("Invalid Token");
        }

        User user = verificationToken.getUser();
        if (user == null) {
            throw new ChangeSetPersister.NotFoundException();
        }

        user.setVerificationToken(null);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
    }
}
