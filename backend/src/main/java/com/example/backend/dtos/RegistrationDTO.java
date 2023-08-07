package com.example.backend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationDTO {
    private String email;
    private String username;
}
