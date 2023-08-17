package com.example.backend.controllers;


import com.example.backend.BackendApplication;
import com.example.backend.dtos.AuthenticationResponse;
import com.example.backend.dtos.LoginRequest;
import com.example.backend.dtos.RegisterRequest;
import com.example.backend.dtos.RegistrationDTO;
import com.example.backend.models.Role;
import com.example.backend.models.User;
import com.example.backend.models.VerificationToken;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.EmailService;
import com.example.backend.services.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @MockBean
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        doAnswer(invocation -> {
            System.out.println("Sending email to: " + invocation.getArgument(0));
            return null;
        }).when(emailService).send(anyString(), anyString(), anyString());
        when(emailService.createVerificationToken()).thenReturn(new VerificationToken());
    }

    @Test
    void shouldRegisterNewUserEntityFromRegisterRequest() {
        HttpHeaders headers = new HttpHeaders();

        RegisterRequest request = new RegisterRequest();
        request.setUsername("Kitty");
        request.setEmail("kitty@example.com");
        request.setPassword("kfjkdfji521");

        RegistrationDTO registrationDTO = RegistrationDTO.builder().username("Kitty").email("kitty@example.com").build();

        HttpEntity<RegisterRequest> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<RegistrationDTO> response = restTemplate
                .exchange(String.format("http://localhost:%s/api/auth/register", port),
                        HttpMethod.POST,
                        httpEntity,
                        RegistrationDTO.class);


        assertEquals(200, response.getStatusCode().value(), response.toString());
        assertEquals(registrationDTO.getEmail(), response.getBody().getEmail());
        assertEquals(registrationDTO.getUsername(), response.getBody().getUsername());
        assertEquals(1, userRepository.count());
    }

    @Test
    void shouldLoginRegisteredUser() {
        User user = User.builder()
                .username("JohnDoe")
                .email("JohnDoe@gmail.com")
                .password(passwordEncoder.encode("12345"))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(savedUser.getEmail());
        loginRequest.setPassword("12345");

        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(loginRequest, headers);

        var response = restTemplate
                .exchange(String.format("http://localhost:%s/api/auth/login", port),
                        HttpMethod.POST,
                        httpEntity,
                        AuthenticationResponse.class);

        assertEquals(200, response.getStatusCode().value(), response.toString());
        assertNotNull(response.getBody().getAccessToken(), response.getBody().getRefreshToken());
    }

    @Test
    void shouldReturnNewRefreshToken() {
        User user = User.builder()
                .username("JohnDoe")
                .email("JohnDoe@gmail.com")
                .password(passwordEncoder.encode("12345"))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        String refreshToken = jwtService.generateRefreshToken(savedUser);
        user.setRefreshTokenUUID(jwtService.extractTokenIdFromRefreshToken(refreshToken));
        userRepository.save(user);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + refreshToken);

        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<AuthenticationResponse> response = restTemplate
                .exchange(String.format("http://localhost:%s/api/auth/refresh", port),
                        HttpMethod.GET,
                        httpEntity,
                        AuthenticationResponse.class);

        assertEquals(200, response.getStatusCode().value(), response.toString());
        assertNotNull(response.getBody().getAccessToken());
        assertNull(response.getBody().getRefreshToken());
    }
}
