package com.rahgozin.appointment.application.model;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "یوزرنیم نیمتواند خالی باشد !")
    private String username;

    @NotNull(message = "پسوورد نیمتواند خالی باشد !")
    private String password;

}
