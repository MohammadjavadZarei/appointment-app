package com.rahgozin.appointment.application.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {

    private String jwtToken;
}
