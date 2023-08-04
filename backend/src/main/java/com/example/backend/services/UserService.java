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

        User newUser = new User(
                userRegistrationDTO.getUsername(),
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getPassword(),
                userRegistrationDTO.getFullName(),
                userRegistrationDTO.getProfilePicture(),
                userRegistrationDTO.getBio()
        );

        return newUser;
    }


}
