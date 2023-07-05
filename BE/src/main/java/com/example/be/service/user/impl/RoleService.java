package com.example.be.service.user.impl;

import com.example.be.entity.user.Role;
import com.example.be.repository.user.IRoleRepository;
import com.example.be.service.user.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findWithName(name);
    }
}