package com.spring_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_security.demo.model.User;
import com.spring_security.demo.repository.UserRepository;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User findByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    
}
