package com.rahgozin.appointment.application.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler
    protected ResponseEntity<Object> handleExceptions(AppointmentException exception){
        ErrorsModel errorsModel = new ErrorsModel();
        ExceptionEnum exceptionEnum = ExceptionEnum.valueOf(exception.getMessage());
        errorsModel.setMessage(exception.getMessage());
        errorsModel.setCode(String.valueOf(exceptionEnum.errorCode));
        return new ResponseEntity<>(errorsModel, exceptionEnum.httpStatus);
    }

}
