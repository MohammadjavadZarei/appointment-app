package com.rahgozin.appointment.application.model;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservedAppointmentFactor {

    private String doctorName;

    private String address;

    private Integer date;

    private String startTime;

    private String endTime;

}
