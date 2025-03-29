package com.hospital_management.controller;

import com.hospital_management.model.Appointment;
import com.hospital_management.model.AppointmentStatus;
import com.hospital_management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public Appointment bookAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestParam String date){
        LocalDateTime appointmentDate = LocalDateTime.parse(date);
        return appointmentService.bookAppointment(patientId, doctorId,appointmentDate);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long doctorId){
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable Long patientId){
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @PutMapping("/update-status")
    public Appointment updateAppointmentStatus(@RequestParam Long appointmentId, @RequestParam AppointmentStatus status){
        return appointmentService.updateAppointmentStatus(appointmentId, status);
    }
}
