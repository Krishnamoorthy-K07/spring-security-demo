package com.spring_security.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name  = "Employee")
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String employeeId;
    private String name;
    private String department;
    private String email;

    
    public Employee() {
    }

    public Employee(Long id, String employeeId, String name, String department, String email) {
        Id = id;
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.email = email;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Id=" + Id +
                ", employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    
}
