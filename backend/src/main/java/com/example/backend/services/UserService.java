package com.example.backend.services;

import com.example.backend.dtos.UserRegistrationDTO;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {

        User newUser = new User();
        newUser.setUsername(userRegistrationDTO.getUsername());
        newUser.setEmail(userRegistrationDTO.getEmail());
        newUser.setPassword(userRegistrationDTO.getPassword());
        newUser.setBio(userRegistrationDTO.getBio());
        newUser.setFullName(userRegistrationDTO.getFullName());
        newUser.setProfilePicture(userRegistrationDTO.getProfilePicture());

        // Save the user to the database using the UserRepository or your preferred data access mechanism.

        return newUser;
    }


}
