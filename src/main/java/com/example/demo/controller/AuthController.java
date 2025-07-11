package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Employee;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody Employee employee) {
        return employeeService.register(employee);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee employee) {
        return employeeService.login(employee);
    }
}
