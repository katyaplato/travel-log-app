package com.example.backend.services;

import com.example.backend.models.Upload;

import java.util.List;

public interface UploadService {
    List<Upload> getNewUploads();
}
