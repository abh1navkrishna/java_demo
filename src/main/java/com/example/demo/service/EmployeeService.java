package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    private JwtUtil jwtUtil;

    public ResponseEntity<?> register(Employee employee) {
        if (repository.findByEmail(employee.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("❌ Email already registered");
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        repository.save(employee);

        return ResponseEntity.ok("✅ Employee registered successfully");
    }

    public ResponseEntity<?> login(Employee employee) {
        var existingEmployee = repository.findByEmail(employee.getEmail());

        if (existingEmployee.isEmpty()) {
            return ResponseEntity.status(401).body("❌ Email not found. Please register first.");
        }

        Employee user = existingEmployee.get();

        if (!passwordEncoder.matches(employee.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("❌ Invalid password. Try again.");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok().body("✅ Login successful. Your JWT Token: " + token);
    }

}
