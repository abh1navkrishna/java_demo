package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.security.JwtUtil;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jawUtil;

    public ResponseEntity<?> register(Employee employee) {
        if (repository.findByEmail(employee.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        repository.save(employee);
        return ResponseEntity.ok("Employee registered successfully");
    }

    public ResponseEntity<?> login(Employee employee) {
        var existingEmployee = repository.findByEmail(employee.getEmail());
        if (existingEmployee.isPresent()
                && passwordEncoder.matches(employee.getPassword(), existingEmployee.get().getPassword())) {
            String tocken = jawUtil.generateToken(employee.getEmail());
            return ResponseEntity.ok("JWT token" + tocken);
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

}
