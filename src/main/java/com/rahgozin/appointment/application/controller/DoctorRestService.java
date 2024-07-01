package com.rahgozin.appointment.application.controller;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.Patient;
import com.rahgozin.appointment.application.model.AddAppointmentRequest;
import com.rahgozin.appointment.application.model.DoctorRegisterRequest;
import com.rahgozin.appointment.application.model.GetAppointmentsRequest;
import com.rahgozin.appointment.application.model.PatientRegisterRequest;
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
import org.springframework.web.context.request.RequestContextHolder;

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
        }catch (Exception e){
            throw e;
        }
    }

    @RequestMapping(value = "/addAppointment",method = RequestMethod.POST)
    public ResponseEntity<List<Appointment>> add(@RequestBody AddAppointmentRequest request){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Appointment> response = appointmentService.addAppointments(username, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/appointments",method = RequestMethod.GET)
    public ResponseEntity<List<Appointment>> getAppointments(GetAppointmentsRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Appointment> response = appointmentService.getAppointments(username, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
