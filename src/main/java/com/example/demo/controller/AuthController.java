package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@RestController
public class AuthController {
    
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody Employee employee){
        return employeeService.register(employee);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee employee){
        return employeeService.login(employee);
    }
}
