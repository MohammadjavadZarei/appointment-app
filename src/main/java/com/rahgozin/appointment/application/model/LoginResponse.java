package com.rahgozin.appointment.application.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {

    private String jwtToken;

    private String refreshToken;

}
