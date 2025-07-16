package com.spring_security.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring_security.demo.service.CustomUserDetailsService;
import com.spring_security.demo.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService uds;
        private final JwtUtil jwtUtil;




    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // public CustomUserDetailsService userDetailsService() {
    //     return uds;
    // }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtUtil, uds);

        http
            // 1️⃣  Permit H2 console without authentication
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/users/**").permitAll()
                // ↙ keep your other rules below
                .requestMatchers(HttpMethod.GET, "/emp/all").hasAnyRole("HR","MANAGER")
                .requestMatchers(HttpMethod.GET, "/emp/**").hasAnyRole("HR","MANAGER","EMPLOYEE")
                .requestMatchers("/emp/**").hasRole("HR")
                .anyRequest().authenticated()
            )

            // 2️⃣  Disable CSRF & frameOptions just for H2
            .csrf(csrf -> csrf.disable()   // skip CSRF for console
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())       // allow frames (H2 UI)
            )

            // 3️⃣  Use default login for everything else
            .logout(Customizer.withDefaults())
            .userDetailsService(uds);

        return http.build();
    
                
            
    }




    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
