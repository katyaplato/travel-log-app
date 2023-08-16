package com.example.backend.services;

import com.example.backend.models.Upload;
import com.example.backend.repositories.UploadRepository;
import com.example.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor

public class UploadServiceImpl implements UploadService{
    private final UploadRepository uploadRepository;
    private final UserRepository userRepository;

    @Override
    public List<Upload> getNewUploads() {
        // You need to implement the logic to retrieve new uploads, for example:
        // Get uploads with uploadDate after a certain point
        LocalDateTime cutoffDateTime = LocalDateTime.now().minusHours(24); // Example: within the last 24 hours
        return uploadRepository.findByUploadDateAfter(cutoffDateTime);
    }
}
