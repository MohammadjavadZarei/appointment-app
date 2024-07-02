package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.entity.Patient;
import com.rahgozin.appointment.application.model.*;

import java.util.List;

public interface PatientService {
    Patient add(PatientRegisterRequest patientRegisterRequest);

    ReservedAppointmentFactor reserveAppointment(ReserveAppointmentRequest request, String patient);

    List<PatientAppointmentModel> get(String userName);
}
