package com.spring_security.demo.controller;

import java.net.URI;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.spring_security.demo.model.Employee;
import com.spring_security.demo.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /* ---------- READ ---------- */

    /** GET /emp/all */
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /** GET /emp/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    /* ---------- CREATE ---------- */

    /** POST /emp */
    @PostMapping
    public ResponseEntity<Employee> save(@Validated @RequestBody Employee employee) {
        Employee created = employeeService.createEmployee(employee);

        // Location: /emp/{newId}
        URI location = URI.create(String.format("/emp/%d", created.getId()));

        return ResponseEntity
                .created(location)   // 201 Created + Location header
                .body(created);
    }

    /* ---------- UPDATE ---------- */

    /** PUT /emp/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(
            @PathVariable Long id,
            @Validated @RequestBody Employee employee) {

        Employee updated = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updated);
    }

    /* ---------- DELETE ---------- */

    /** DELETE /emp/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();   // 204 No Content
    }
}
