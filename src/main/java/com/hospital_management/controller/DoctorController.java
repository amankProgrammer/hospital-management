package com.hospital_management.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping("/dashboard")
    public String doctorDashboard() {
        return "Welcome to Doctor Dashboard!";
    }
}
