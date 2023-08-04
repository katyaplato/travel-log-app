package com.example.backend.dtos;

import lombok.Data;

import java.time.LocalDate;
@Data
public class UserGetInfoDTO {
    private String username;
    private String fullName;
    private String profilePicture;
    private String bio;
    private LocalDate creationDate;
}
