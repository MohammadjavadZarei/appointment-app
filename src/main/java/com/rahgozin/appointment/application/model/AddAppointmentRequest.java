package com.rahgozin.appointment.application.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddAppointmentRequest {

    private String startTime;

    private String endTime;

    private Integer day;

}
