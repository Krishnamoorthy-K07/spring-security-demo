package com.spring_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_security.demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
}
