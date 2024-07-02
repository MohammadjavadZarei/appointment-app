package com.rahgozin.appointment.application.exception;

public class AppointmentException extends RuntimeException{

    public AppointmentException() {
        super();
    }

    public AppointmentException(Throwable cause) {
        super(cause);
    }

    public AppointmentException(ExceptionEnum message) {
        super(String.valueOf(message));
    }
}
