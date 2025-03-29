package com.hospital_management.controller;

import com.hospital_management.model.User;
import com.hospital_management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/approve/{patientId}")
    public User approvePatient(@PathVariable Long patientId) {
        return adminService.approvePatient(patientId);
    }

    @DeleteMapping("/reject/{patientId}")
    public User rejectPatient(@PathVariable Long patientId) {
        return adminService.rejectPatient(patientId);
    }
}
