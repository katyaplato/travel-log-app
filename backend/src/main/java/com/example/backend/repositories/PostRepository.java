package com.example.backend.repositories;

import com.example.backend.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository <Post, Long> {
}
