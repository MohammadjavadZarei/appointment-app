package com.rahgozin.appointment.application.repository;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.AppointmentStatus;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

    List<Appointment> findAllByActionDateAndDoctorAndStatusIn(Integer date, DoctorEntity doctor, List<AppointmentStatus> statuses);

    Appointment findByIdAndStatus(Long id, AppointmentStatus status);

    List<Appointment> findAllByActionDateAndDoctorAndStatus(Integer date, DoctorEntity doctor, AppointmentStatus status);

}
