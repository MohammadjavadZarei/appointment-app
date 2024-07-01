package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.model.DoctorRegisterRequest;

import java.util.List;

public interface DoctorService {

    DoctorEntity add(DoctorRegisterRequest doctorRegisterRequest);

    List<DoctorEntity> getAllDoctors();
}
