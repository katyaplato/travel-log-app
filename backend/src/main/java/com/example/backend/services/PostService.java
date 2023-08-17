package com.example.backend.services;

import com.example.backend.models.Post;

public interface PostService {
    void validateNewPost(Post post);
    Post updateDescription(Long id, String description);
}
