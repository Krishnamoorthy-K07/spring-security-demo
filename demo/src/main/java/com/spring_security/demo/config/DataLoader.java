package com.spring_security.demo.config;


import jakarta.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.spring_security.demo.model.Employee;
import com.spring_security.demo.model.Role;
import com.spring_security.demo.model.User;
import com.spring_security.demo.repository.EmployeeRepository;
import com.spring_security.demo.repository.RoleRepository;
import com.spring_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Component          // gets picked up by component‑scan
public class DataLoader {

    private final EmployeeRepository repo;
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;

    public DataLoader(EmployeeRepository repo, UserRepository userRepo, RoleRepository roleRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @PostConstruct
    public void addSampleEmployees() {
        // only seed if the table is empty (idempotent)
        if (repo.count() == 0) {
            List<Employee> batch = List.of(
                new Employee(null, "EMP1001", "Krishnamoorthy",  "Engineering", "krish1@example.com"),
                new Employee(null, "EMP1002", "Sriyadharshini",   "Marketing",   "sriya@example.com"),
                new Employee(null, "EMP1003", "Rakshana",        "HR",         "raksha@example.com"),
                new Employee(null, "EMP1004", "Arjun",           "Finance",    "arjun@example.com"),
                new Employee(null, "EMP1005", "Meera",           "Engineering","meera@example.com"),
                new Employee(null, "EMP1006", "Rahul",           "Support",    "rahul@example.com"),
                new Employee(null, "EMP1007", "Divya",           "Engineering","divya@example.com"),
                new Employee(null, "EMP1008", "Santhosh",        "IT",         "santhosh@example.com"),
                new Employee(null, "EMP1009", "Ananya",          "R&D",        "ananya@example.com"),
                new Employee(null, "EMP1010", "Vignesh",         "Sales",      "vignesh@example.com"),
                new Employee(null, "EMP1011", "Priya",           "Design",     "priya@example.com")
            );
            repo.saveAll(batch);
        }
    }

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder){
        return args -> 
        {
       // 1. Ensure roles exist
        Role hrRole      = roleRepo.findByRoleName("HR");
        Role mgrRole     = roleRepo.findByRoleName("MANAGER");
        Role empRole     = roleRepo.findByRoleName("EMPLOYEE");

        if (hrRole == null)  hrRole  = roleRepo.save(new Role(null, "HR",      Set.of()));
        if (mgrRole == null) mgrRole = roleRepo.save(new Role(null, "MANAGER", Set.of()));
        if (empRole == null) empRole = roleRepo.save(new Role(null, "EMPLOYEE",Set.of()));

        // 2. Create one User per Employee if not already present
        for (Employee emp : repo.findAll()) {

            // userName = employeeCode, e.g. EMP1001
            if (userRepo.findByUserName(emp.getEmployeeId()).isPresent()) {
                continue; // already seeded
            }

            // decide role
            Role assignedRole;
            switch (emp.getEmployeeId()) {
                case "EMP1003" -> assignedRole = hrRole;
                case "EMP1002" -> assignedRole = mgrRole;
                default        -> assignedRole = empRole;
            }

            User user = new User(
                    null,
                    emp.getEmployeeId(),                // username
                    encoder.encode("pass123"),        // password (BCrypt)
                    emp.getName(),               // reuse names from employee
                    "",                               // lastName blank for demo
                    Set.of(assignedRole),
                    emp
            );

            userRepo.save(user);
        }
    };
}
    
}
