package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.model.DoctorRegisterRequest;
import com.rahgozin.appointment.application.model.GetDoctorAppointmentsRequest;

import java.util.List;

public interface DoctorService {

    DoctorEntity add(DoctorRegisterRequest doctorRegisterRequest);

    List<DoctorEntity> getAllDoctors();

    List<Appointment> getDoctorAppointments(GetDoctorAppointmentsRequest request);

}
