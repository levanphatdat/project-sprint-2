package com.example.be.service.user;

import com.example.be.entity.user.Role;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName (String name);
}
