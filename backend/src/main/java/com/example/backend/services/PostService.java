package com.example.backend.services;

import com.example.backend.models.Post;
import com.example.backend.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void validateNewPost(Post post) {
        if (ObjectUtils.isEmpty(post)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Post updateDescription(Long id, String description){
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()){
            throw new Error ("Post does not exist.");
        }
        Post postToUpdate = optionalPost.get();
        postToUpdate.setDescription(description);
        return postToUpdate;
    }
}
