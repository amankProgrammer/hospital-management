package com.hospital_management.service;

import com.hospital_management.model.Appointment;
import com.hospital_management.model.AppointmentStatus;
import com.hospital_management.model.User;
import com.hospital_management.repository.AppointmentRepository;
import com.hospital_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime date){
        User patient = userRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        User doctor= userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(date);
        appointment.setStatus(AppointmentStatus.PENDING);
        return appointmentRepository.save(appointment);
    }
    public List<Appointment> getAppointmentsByDoctor(Long doctorId){
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        return appointmentRepository.findByDoctor(doctor);
    }
    public List<Appointment> getAppointmentsByPatient(Long patientId){
        User patient = userRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        return appointmentRepository.findByDoctor(patient);
    }
    public Appointment updateAppointmentStatus(Long appointmentId, AppointmentStatus status){
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if(appointmentOpt.isPresent()){
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("Appointment not found");
    }
}
