package com.spring_security.demo.service;

import java.util.Set;

import javax.naming.directory.InvalidAttributesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring_security.demo.config.SecurityConfig;
import com.spring_security.demo.model.Employee;
import com.spring_security.demo.model.Role;
import com.spring_security.demo.model.User;
import com.spring_security.demo.repository.EmployeeRepository;
import com.spring_security.demo.repository.RoleRepository;
import com.spring_security.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository; 
    }

    public User findByUsername(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User register(User user) throws InvalidAttributesException {
        if (user.getUserName().isEmpty() && user.getPassword().isEmpty() && user.getFirstName().isEmpty() && user.getEmployee() == null) {
            throw new InvalidAttributesException("Please provide the required data to register user");
        }

        Employee emp = user.getEmployee();
        if (emp != null) {
            employeeRepository.save(emp); // Save employee first
        }

        user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
        Role defaultRole = roleRepository.findByRoleName("EMPLOYEE");
        user.setRoles(Set.of(defaultRole));
        User savedUser = userRepository.save(user);
        return savedUser;

    }

    public User login(User user) throws Exception {
        if (user.getUserName().isEmpty() && user.getPassword().isEmpty()) {
            throw new InvalidAttributesException("Please provide the required data to login user");
        }
        User loggedUser = userRepository.findByUserName(user.getUserName())
                .orElseThrow(() -> new Exception("\"NOT_FOUND\""));
        return loggedUser;
    }

}
