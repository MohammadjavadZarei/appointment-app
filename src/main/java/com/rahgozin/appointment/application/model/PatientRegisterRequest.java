package com.rahgozin.appointment.application.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PatientRegisterRequest {
    private String name;

    private String username;

    private String password;

    private String email;

    private String address;

    private Long mobileNumber;
}
