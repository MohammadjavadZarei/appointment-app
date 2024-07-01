package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.entity.Patient;
import com.rahgozin.appointment.application.entity.Role;
import com.rahgozin.appointment.application.model.PatientRegisterRequest;
import com.rahgozin.appointment.application.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;
    @Override
    public Patient add(PatientRegisterRequest request) {
        Patient patient = new Patient();
        patient.setUsername(request.getUsername());
        patient.setPassword(request.getPassword());
        patient.setName(request.getName());
        patient.setMobileNumber(request.getMobileNumber());
        patient.setRole(Role.PATIENT);
        patientRepository.save(patient);
        return patient;
    }
}
