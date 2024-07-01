package com.rahgozin.appointment.application.repository;

import com.rahgozin.appointment.application.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
