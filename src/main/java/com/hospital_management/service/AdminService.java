package com.hospital_management.service;

import com.hospital_management.model.Role;
import com.hospital_management.model.User;
import com.hospital_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public User approvePatient(Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (!patient.getRole().equals(Role.PATIENT)) {
            throw new RuntimeException("Only patients can be approved.");
        }

        patient.setApproved(true);
        return userRepository.save(patient);
    }

    public User rejectPatient(Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (!patient.getRole().equals(Role.PATIENT)) {
            throw new RuntimeException("Only patients can be rejected.");
        }

        userRepository.delete(patient);
        return patient;
    }
}
