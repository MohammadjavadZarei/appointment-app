package com.rahgozin.appointment.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class DoctorRegisterRequest {

    private String name;

    private String username;

    private String password;

    private String email;

    private String address;

    private String field;
}
