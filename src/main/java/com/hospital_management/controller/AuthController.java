package com.hospital_management.controller;


import com.hospital_management.model.Role;
import com.hospital_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// This creates APIs for user registration & login

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody Map<String,String> request){
        try {
            Role role = Role.valueOf(request.get("role").toUpperCase()); // ✅ Convert string to Enum
            return authService.registerUser(request.get("username"), request.get("email"), request.get("password"), role);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role. Allowed roles: ADMIN, DOCTOR, PATIENT");
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> request){
        return authService.loginUser(request.get("username"),request.get("password"));
    }
}
