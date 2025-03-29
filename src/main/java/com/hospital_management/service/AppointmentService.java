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

    @Autowired
    private EmailService emailService;

    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDateTime date){
        User patient = userRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
        User doctor= userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));

        LocalDateTime appointmentDate = date;

        // **Check if the doctor is already booked at the given time**
        Optional<Appointment> existingAppointment =
                appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, appointmentDate);

        if (existingAppointment.isPresent()) {
            throw new RuntimeException("Doctor is already booked at this time. Please choose another slot.");
        }

        // **If no conflict, create the appointment**
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(date);
        appointment.setStatus(AppointmentStatus.PENDING);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // 📧 Send Email Notification to Patient & Doctor
        String subject = "Appointment Confirmation";
        String message = "Dear " + patient.getUsername() + ",\n\nYour appointment with Dr. " +
                doctor.getUsername() + " is scheduled for " + date + ".\n\nThank you!";

        emailService.sendEmail(patient.getEmail(), subject, message);
        emailService.sendEmail(doctor.getEmail(), subject, "New appointment scheduled with " + patient.getUsername());

        return savedAppointment;
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
            appointmentRepository.save(appointment);

            // 📧 Notify Patient
            String subject = "Appointment Status Update";
            String message = "Dear " + appointment.getPatient().getUsername() + ",\n\n" +
                    "Your appointment with Dr. " + appointment.getDoctor().getUsername() +
                    " has been updated to: " + status + ".\n\nThank you!";

            emailService.sendEmail(appointment.getPatient().getEmail(), subject, message);

            return appointment;
        }
        throw new RuntimeException("Appointment not found");
    }
}
