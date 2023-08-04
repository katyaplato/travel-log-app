package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String profilePicture;
    private String bio;
}
