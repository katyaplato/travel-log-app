package com.example.backend.repositories;

import com.example.backend.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository <Post, Long> {
    List<Post> findByLocation(String location);
}
