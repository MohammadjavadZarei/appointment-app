package com.rahgozin.appointment.application.model;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.Patient;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DoctorAppointmentModel {

    private Appointment appointment;

    private Patient patient;
}
