package com.example.backend.repositories;

import com.example.backend.models.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UploadRepository extends JpaRepository <Upload, Long> {
    List<Upload> findByUploadDateAfter(LocalDateTime uploadDate);
}

