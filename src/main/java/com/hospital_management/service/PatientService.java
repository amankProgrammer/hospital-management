package com.hospital_management.service;

import com.hospital_management.model.Patient;
import com.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

// This provides methods to register and fetch patients

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }
    public Optional<Patient> getPatientByEmail (String email){
        return patientRepository.findByEmail(email);
    }
    public Patient registerPatient (Patient patient){
        return patientRepository.save(patient);
    }
}
