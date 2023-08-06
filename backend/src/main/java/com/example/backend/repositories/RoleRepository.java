package com.example.backend.repositories;

import com.example.backend.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository <Role, Long> {
}
