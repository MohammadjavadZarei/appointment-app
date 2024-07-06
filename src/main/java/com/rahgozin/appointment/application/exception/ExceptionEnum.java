package com.rahgozin.appointment.application.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionEnum {
    START_TIME_SOONER_THAN_END_TIME(400,"START TIME SOONER THAN END", HttpStatus.BAD_REQUEST),
    APPOINTMENT_CANCELED(400,"appointment is canceled by doctor", HttpStatus.BAD_REQUEST),
    APPOINTMENT_RESERVED(400,"appointment is already reserved", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_FOUND(400,"appointment not found", HttpStatus.BAD_REQUEST),
    APPOINTMENT_DOES_NOT_BELONG_TO_DOCTOR(400,"you can not cancel this appointment", HttpStatus.BAD_REQUEST),
    ENTER_MOB_FIRST(400,"you should fill your mobile number at first", HttpStatus.BAD_REQUEST),
    APPOINTMENT_ALREADY_CANCELED_BY_DOCTOR(400,"appointment already canceled by doctor", HttpStatus.BAD_REQUEST),
    APPOINTMENT_ALREADY_RESERVED_BY_PATIENT(400,"appointment already reserved by patient", HttpStatus.BAD_REQUEST)
    ;

    final long errorCode;
    final String message;
    final HttpStatus httpStatus;

    ExceptionEnum(long errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
