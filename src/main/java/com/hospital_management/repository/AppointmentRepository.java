package com.hospital_management.repository;

import com.hospital_management.model.Appointment;
import com.hospital_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByDoctor(User doctor);
    List<Appointment> findByPatient(User patient);
}
