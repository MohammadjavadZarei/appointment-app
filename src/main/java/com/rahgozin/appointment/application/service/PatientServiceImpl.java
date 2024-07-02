package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.AppointmentStatus;
import com.rahgozin.appointment.application.entity.Patient;
import com.rahgozin.appointment.application.entity.Role;
import com.rahgozin.appointment.application.model.PatientAppointmentModel;
import com.rahgozin.appointment.application.model.PatientRegisterRequest;
import com.rahgozin.appointment.application.model.ReserveAppointmentRequest;
import com.rahgozin.appointment.application.model.ReservedAppointmentFactor;
import com.rahgozin.appointment.application.repository.AppointmentRepository;
import com.rahgozin.appointment.application.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    private final AppointmentRepository appointmentRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public Patient add(PatientRegisterRequest request) {
        Patient patient = new Patient();
        patient.setUsername(request.getUsername());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setName(request.getName());
        patient.setMobileNumber(request.getMobileNumber());
        patient.setRole(Role.PATIENT);
        patientRepository.save(patient);
        return patient;
    }

    @Override
    public ReservedAppointmentFactor reserveAppointment(ReserveAppointmentRequest request, String username) {
        Appointment appointment = appointmentRepository.findByIdAndStatus(request.getAppointmentId(), AppointmentStatus.EMPTY);
        Patient patient = patientRepository.findByUsername(username);
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.RESERVED);
        try {
            appointmentRepository.save(appointment);

        }catch (Exception e){
            e.printStackTrace();
        }
        return ReservedAppointmentFactor.builder().
                address(appointment.getDoctor().getAddress()).
                doctorName(appointment.getDoctor().getName()).
                endTime(String.valueOf(appointment.getEndTime())).
                startTime(String.valueOf(appointment.getStartTime())).
                date(appointment.getActionDate()).
                build();
    }

    @Override
    public List<PatientAppointmentModel> get(String userName) {
        List<PatientAppointmentModel> models = new ArrayList<>();
        List<Appointment> appointments =  patientRepository.findByUsername(userName).getAppointments();
        for (Appointment appointment : appointments){
            PatientAppointmentModel appointmentModel = new PatientAppointmentModel();
            appointmentModel.setAppointment(appointment);
            appointmentModel.setDoctor(appointment.getDoctor());
            models.add(appointmentModel);
        }
        return models;
    }
}
