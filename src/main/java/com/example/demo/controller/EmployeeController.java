package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/me")
    public Employee getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return employeeRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}
