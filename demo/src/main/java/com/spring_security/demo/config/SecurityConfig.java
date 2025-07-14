package com.spring_security.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spring_security.demo.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService uds;

    // public CustomUserDetailsService userDetailsService() {
    //     return uds;
    // }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1️⃣  Permit H2 console without authentication
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                // ↙ keep your other rules below
                .requestMatchers(HttpMethod.GET, "/emp/all").hasAnyRole("HR","MANAGER")
                .requestMatchers(HttpMethod.GET, "/emp/**").hasAnyRole("HR","MANAGER","EMPLOYEE")
                .requestMatchers("/emp/**").hasRole("HR")
                .anyRequest().authenticated()
            )

            // 2️⃣  Disable CSRF & frameOptions just for H2
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")   // skip CSRF for console
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())       // allow frames (H2 UI)
            )

            // 3️⃣  Use default login for everything else
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults()) 
            .logout(Customizer.withDefaults())
            .userDetailsService(uds);

        return http.build();
    
                
            
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
