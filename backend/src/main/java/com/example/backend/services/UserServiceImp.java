package com.example.backend.services;

import com.example.backend.dtos.UserGetInfoDTO;
import com.example.backend.dtos.UserRegistrationDTO;
import com.example.backend.models.Post;
import com.example.backend.models.Subscription;
import com.example.backend.models.User;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public UserGetInfoDTO getUserInfo(Long id){
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new Error("User does not exist");
        }
        return new UserGetInfoDTO(optionalUser.get());
    }


    public boolean saveUser(User user) {
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            return false;
        }

        //user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean isEmailTaken(String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }

    @Override
    public List<String> getSubscriberEmailsForUser(String subscribedUserName) {
        List<String> subscriberEmails = new ArrayList<>();

        Optional<User> subscribedUser = userRepository.findByUsername(subscribedUserName);

        return subscriberEmails;
    }

    @Override
    public void updateUserInfo(Long id, String loggedInUsername, String userName, String fullName, String bio) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new Error("User not found.");
        }
        User user = optionalUser.get();
        if(user.getEmail().equals(loggedInUsername)) {
            user.setUsername(userName);
            user.setFullName(fullName);
            user.setBio(bio);
            userRepository.save(user);

        } else {
            throw new Error("Invalid user ID.");
        }
    }
}
