package com.rahgozin.appointment.application.model;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class PatientAppointmentModel {
    private Appointment appointment;

    private DoctorEntity doctor;
}
