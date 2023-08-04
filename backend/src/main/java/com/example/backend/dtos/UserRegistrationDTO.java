package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class UserRegistrationDTO {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String profilePicture;

}
