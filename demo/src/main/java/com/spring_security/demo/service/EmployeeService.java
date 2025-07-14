package com.spring_security.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring_security.demo.model.Employee;


@Service
public interface EmployeeService {

    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    Employee deleteEmployee(Long id);
    
}
