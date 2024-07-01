package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.model.AddAppointmentRequest;
import com.rahgozin.appointment.application.model.GetAppointmentsRequest;

import java.util.List;

public interface AppointmentService {

    List<Appointment> addAppointments(String username,AddAppointmentRequest request);
    List<Appointment> getAppointments(String username, GetAppointmentsRequest request);
}
