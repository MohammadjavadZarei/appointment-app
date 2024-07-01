package com.rahgozin.appointment.application.model;

import com.rahgozin.appointment.application.entity.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAppointmentsRequest {
    private List<AppointmentStatus> statuses;

    private Integer date;

}
