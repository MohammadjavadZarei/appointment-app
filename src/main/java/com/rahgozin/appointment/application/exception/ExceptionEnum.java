package com.rahgozin.appointment.application.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionEnum {
    START_TIME_SOONER_THAN_END_TIME(400,"START TIME SOONER THAN END", HttpStatus.BAD_REQUEST)
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
