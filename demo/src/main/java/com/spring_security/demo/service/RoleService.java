package com.spring_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_security.demo.model.Role;
import com.spring_security.demo.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
