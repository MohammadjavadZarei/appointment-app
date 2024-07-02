package com.rahgozin.appointment.application.controller;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.entity.Patient;
import com.rahgozin.appointment.application.model.*;
import com.rahgozin.appointment.application.service.DoctorService;
import com.rahgozin.appointment.application.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Patient> add(PatientRegisterRequest request) throws Exception {
        try {
            return new ResponseEntity<>(patientService.add(request), HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/getAllDoctors", method = RequestMethod.GET)
    public ResponseEntity<List<DoctorEntity>> getAll() throws Exception {
        try {
            return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }
    @RequestMapping(value = "/getOpenAppointments", method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> getAllOpenAppointments(GetDoctorAppointmentsRequest request) throws Exception {
        try {
            return new ResponseEntity<>(doctorService.getDoctorAppointments(request), HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }
    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public ResponseEntity<ReservedAppointmentFactor> reserve(@RequestBody ReserveAppointmentRequest request) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return new ResponseEntity<>(patientService.reserveAppointment(request, username), HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<List<PatientAppointmentModel>> get() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return new ResponseEntity<>(patientService.get(username), HttpStatus.OK);
        }catch (Exception e){
            throw e;
        }
    }
}
