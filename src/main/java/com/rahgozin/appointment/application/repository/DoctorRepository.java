package com.rahgozin.appointment.application.repository;

import com.rahgozin.appointment.application.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long>{

}
