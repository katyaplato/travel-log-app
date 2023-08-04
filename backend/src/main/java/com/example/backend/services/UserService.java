package com.example.backend.services;

import com.example.backend.dtos.UserRegistrationDTO;
import com.example.backend.models.Post;
import com.example.backend.models.Subscription;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Post> getAllPosts(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new Error("User does not exist");
        }
        return optionalUser.get().getPosts();
    }

    public List<Subscription> getAllSubscriptions(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new Error("User does not exist");
        }
        return optionalUser.get().getSubscriptions();
    }


}
