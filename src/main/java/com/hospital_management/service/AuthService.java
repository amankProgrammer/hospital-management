package com.hospital_management.service;

import com.hospital_management.model.Role;
import com.hospital_management.model.User;
import com.hospital_management.repository.UserRepository;
import com.hospital_management.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// This handles user registration & login authentication.

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(String username, String email, String password, Role role){
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("user already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);
        return "user registered successfully";
    }

    public String loginUser(String username , String password){
        Optional <User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(passwordEncoder.matches(password, user.getPassword())) {
                return jwtUtil.generateToken(username);
            }
        }
        throw  new RuntimeException("Invalid Credentials");
    }
}
