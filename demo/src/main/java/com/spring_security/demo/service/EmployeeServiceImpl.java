package com.spring_security.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_security.demo.model.Employee;
import com.spring_security.demo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow();
    }
    
    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(employee.getName());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setEmployeeId(employee.getEmployeeId());
        return employeeRepository.save(existingEmployee);
    }
    
    @Override
    public Employee deleteEmployee(Long id) {
        Employee existingEmployee = getEmployeeById(id);
        employeeRepository.delete(existingEmployee);
        return existingEmployee;
    }
    
}
