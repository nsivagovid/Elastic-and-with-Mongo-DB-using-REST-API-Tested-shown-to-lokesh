package com.example.spring_elastic.controller;

import com.example.spring_elastic.model.EmployeeElasticsearch;
import com.example.spring_elastic.model.EmployeeMongo;
import com.example.spring_elastic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Add Employee to MongoDB and Elasticsearch
    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeMongo employee) throws IOException {
        employeeService.saveEmployee(employee);
        return ResponseEntity.ok("Employee added successfully.");
    }


    // Get Employee from MongoDB by ID
    @GetMapping("/mongo/{id}")
    public ResponseEntity<EmployeeMongo> getEmployeeFromMongoDBById(@PathVariable String id) {
        Optional<EmployeeMongo> employee = employeeService.getEmployeeFromMongoDBById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get Employee from Elasticsearch by ID
    @GetMapping("/elastic/{id}")
    public ResponseEntity<EmployeeElasticsearch> getEmployeeFromElasticsearchById(@PathVariable String id) throws IOException {
        Optional<EmployeeElasticsearch> employee = employeeService.getEmployeeFromElasticsearchById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete Employee by ID from MongoDB and Elasticsearch
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) throws IOException {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }
}
