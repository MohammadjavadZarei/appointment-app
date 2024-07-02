package com.rahgozin.appointment.application;

import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.entity.Role;
import com.rahgozin.appointment.application.entity.User;
import com.rahgozin.appointment.application.model.AddAppointmentRequest;
import com.rahgozin.appointment.application.repository.AppointmentRepository;
import com.rahgozin.appointment.application.repository.DoctorRepository;
import com.rahgozin.appointment.application.repository.UserRepository;
import com.rahgozin.appointment.application.service.AppointmentService;
import com.rahgozin.appointment.application.service.AppointmentServiceImp;
import com.rahgozin.appointment.application.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class ServiceTest {


    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private AppointmentRepository appointmentRepository;


    @InjectMocks
    private AppointmentServiceImp appointmentService = new AppointmentServiceImp(userRepository, appointmentRepository);

    private DoctorEntity doctor;

    @BeforeEach
    public void setUp() {

        doctor = new DoctorEntity();
        doctor.setId(555L);
        doctor.setUsername("doctor");
        doctor.setRole(Role.DOCTOR);
        doctor.setPassword("123456");
        doctor.setName("rezaei");
        doctorRepository.save(doctor);
    }

    @Test
    public void testAddAppointments() {
        AddAppointmentRequest request = new AddAppointmentRequest();
        request.setStartTime("09:00");
        request.setEndTime("11:00");
        request.setDay(20240702);
        DoctorEntity doctor1 = doctorRepository.findByUsername("rezaei");
        given(appointmentRepository.save(any(Appointment.class))).willReturn(new Appointment());
    }
}
