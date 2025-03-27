package com.hospital_management.controller;

import com.hospital_management.model.Patient;
import com.hospital_management.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/* This creates Rest APIs to :
1. Register a patient (/api/patients/register)
2. Get all patients (/api/patients)
3. Get patients by email (/api/patients/{email}) */

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    @PostMapping("/register")
    public Patient registerPatient (@RequestBody Patient patient){
        return patientService.registerPatient(patient);
    }

    @GetMapping("/{email}")
    public Optional<Patient> getPatientByEmail(@PathVariable String email){
        return patientService.getPatientByEmail(email);
    }
}
