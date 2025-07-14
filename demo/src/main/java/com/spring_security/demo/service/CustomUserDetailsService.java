package com.spring_security.demo.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring_security.demo.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserService userService;
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        

        User user = userService.findByUsername(username);
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                                                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                                                    .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
        
    }
    
}
