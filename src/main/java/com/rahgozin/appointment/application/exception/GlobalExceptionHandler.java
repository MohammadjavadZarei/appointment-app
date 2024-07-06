package com.rahgozin.appointment.application.exception;

import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler
    protected ResponseEntity<Object> handleExceptions(AppointmentException exception){
        ErrorsModel errorsModel = new ErrorsModel();
        ExceptionEnum exceptionEnum = ExceptionEnum.valueOf(exception.getMessage());
        errorsModel.setMessage(exceptionEnum.message);
        errorsModel.setCode(String.valueOf(exceptionEnum.errorCode));
        return new ResponseEntity<>(errorsModel, exceptionEnum.httpStatus);
    }
    @ExceptionHandler
    protected ResponseEntity<Object> handleDuplicateException(SQLIntegrityConstraintViolationException exception){
        ErrorsModel errorsModel = new ErrorsModel();
        errorsModel.setMessage("appointment already exists");
        errorsModel.setCode("400");
        return new ResponseEntity<>(errorsModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handleConcurrencyException(StaleObjectStateException exception){
        ErrorsModel errorsModel = new ErrorsModel();
        errorsModel.setMessage("appointment status has been changed by another user");
        errorsModel.setCode("400");
        return new ResponseEntity<>(errorsModel, HttpStatus.BAD_REQUEST);
    }

}
