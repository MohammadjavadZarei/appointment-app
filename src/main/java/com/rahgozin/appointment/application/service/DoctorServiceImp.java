package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.AppointmentStatus;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.entity.Role;
import com.rahgozin.appointment.application.model.DoctorRegisterRequest;
import com.rahgozin.appointment.application.model.GetDoctorAppointmentsRequest;
import com.rahgozin.appointment.application.repository.AppointmentRepository;
import com.rahgozin.appointment.application.repository.DoctorRepository;
import com.rahgozin.appointment.application.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorServiceImp implements DoctorService {

    private final PasswordEncoder passwordEncoder;

    private final DoctorRepository doctorRepository;

    private final AppointmentRepository appointmentRepository;
    @Override
    public DoctorEntity add(DoctorRegisterRequest request) {
        DoctorEntity user = new DoctorEntity();
        user.setAddress(request.getAddress());
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.DOCTOR);
        user.setField(request.getField());
        try {
            doctorRepository.save(user);
        }catch (Exception e){
            throw e;
        }
        return user;
    }

    @Override
    public List<DoctorEntity> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Appointment> getDoctorAppointments(GetDoctorAppointmentsRequest request) {
        Optional<DoctorEntity> doctor = doctorRepository.findById(request.getDoctorId());
        return doctor.map(doctorEntity -> appointmentRepository.findAllByActionDateAndDoctorAndStatus(request.getDay(), doctorEntity, AppointmentStatus.EMPTY)).orElse(null);
    }


}
