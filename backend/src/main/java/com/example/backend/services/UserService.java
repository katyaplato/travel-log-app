package com.example.backend.services;

import com.example.backend.dtos.UserGetInfoDTO;
import com.example.backend.models.Post;
import com.example.backend.models.Subscription;
import com.example.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;

public interface UserService {
    List<Post> getAllPosts(Long id);

    List<Subscription> getAllSubscriptions(Long id);

    UserGetInfoDTO getUserInfo(Long id);
}
