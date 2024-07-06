package com.rahgozin.appointment.application.controller;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.exception.AppointmentException;
import com.rahgozin.appointment.application.exception.ExceptionEnum;
import com.rahgozin.appointment.application.model.*;
import com.rahgozin.appointment.application.service.AppointmentService;
import com.rahgozin.appointment.application.service.DoctorService;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/doctor")
public class DoctorRestService {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<DoctorEntity> add(DoctorRegisterRequest request) throws Exception {
        try {
            return new ResponseEntity<>(doctorService.add(request), HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/addAppointment", method = RequestMethod.POST)
    public ResponseEntity<List<Appointment>> add(@RequestBody AddAppointmentRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(request.getStartTime(), formatter);
        LocalTime end = LocalTime.parse(request.getEndTime(), formatter);
        if (end.isBefore(start)){
            throw new AppointmentException(ExceptionEnum.START_TIME_SOONER_THAN_END_TIME);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Appointment> response = appointmentService.addAppointments(username, start, end, request.getDay());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/cancelAppointment", method = RequestMethod.POST)
    public ResponseEntity<List<Appointment>> cancel(@RequestBody CancelAppointmentRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(appointmentService.cancelAppointments(request.getAppointmentIds(), username), HttpStatus.OK);
    }



    @RequestMapping(value = "/appointments", method = RequestMethod.GET)
    public ResponseEntity<List<DoctorAppointmentModel>> getAppointments(GetAppointmentsRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(appointmentService.getAppointments(username, request), HttpStatus.OK);
    }
}
