package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.model.AddAppointmentRequest;
import com.rahgozin.appointment.application.model.DoctorAppointmentModel;
import com.rahgozin.appointment.application.model.GetAppointmentsRequest;

import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    List<Appointment> addAppointments(String username, LocalTime startTime, LocalTime endTime, Integer day);
    List<DoctorAppointmentModel> getAppointments(String username, GetAppointmentsRequest request);
}
