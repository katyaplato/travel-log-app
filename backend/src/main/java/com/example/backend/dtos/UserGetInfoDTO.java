package com.example.backend.dtos;

import com.example.backend.models.User;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UserGetInfoDTO {
    private String username;
    private String fullName;
    private String profilePicture;
    private String bio;
    private LocalDate creationDate;
    public UserGetInfoDTO(User user){
        this.bio = user.getBio();
        this.creationDate = user.getCreationDate();
        this.fullName = user.getFullName();
        this.profilePicture = user.getProfilePicture();
        this.username = user.getUsername();
    }
}
