package com.spring_security.demo.controller;

import java.util.Map;
import java.util.Objects;

import javax.naming.directory.InvalidAttributesException;
import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.*;

import com.spring_security.demo.model.AuthRequest;
import com.spring_security.demo.model.User;
import com.spring_security.demo.repository.UserRepository;
import com.spring_security.demo.service.UserService;
import com.spring_security.demo.util.JwtUtil;



@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    

    @Autowired
    public UserController(UserService userService,AuthenticationManager authManager,JwtUtil jwtUtil, UserRepository userRepository) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;

    }


    @PostMapping("/register")
    public String register(@RequestBody User user) throws InvalidAttributesException{
        User registeredUser  = userService.register(user);
        if(Objects.isNull(registeredUser))
            return "Failure";
        return "SUCCESS";
    }

    
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody AuthRequest request) {

    User user = userRepository.findByUserName(request.getUsername()).orElseThrow();
    System.out.println("USER FIRST NAME : "+ user.getFirstName());
    try {
        org.springframework.security.core.Authentication auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    String token = jwtUtil.generateToken(request.getUsername());
    return ResponseEntity.ok(Map.of("token", token));
        
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
    }  catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
    }
}
    

    
}
   



   

