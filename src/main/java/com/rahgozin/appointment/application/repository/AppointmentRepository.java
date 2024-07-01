package com.rahgozin.appointment.application.repository;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.AppointmentStatus;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

    List<Appointment> findAllByActionDateAndDoctorAndStatusIn(Integer date, DoctorEntity doctor, List<AppointmentStatus> statuses);
}
