package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.entity.Patient;
import com.rahgozin.appointment.application.model.DoctorRegisterRequest;
import com.rahgozin.appointment.application.model.PatientRegisterRequest;

public interface PatientService {
    Patient add(PatientRegisterRequest patientRegisterRequest);
}
