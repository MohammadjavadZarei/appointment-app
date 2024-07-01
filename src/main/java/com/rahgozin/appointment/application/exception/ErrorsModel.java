package com.rahgozin.appointment.application.exception;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorsModel {

    String code;

    String message;

    public ErrorsModel(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorsModel() {
    }
}
