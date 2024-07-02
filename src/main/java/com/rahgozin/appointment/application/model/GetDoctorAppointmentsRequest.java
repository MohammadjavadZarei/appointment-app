package com.rahgozin.appointment.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class GetDoctorAppointmentsRequest {

    private Long doctorId;

    private Integer day;
}
