package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.util.Dateutil;
import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.AppointmentStatus;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.entity.User;
import com.rahgozin.appointment.application.exception.AppointmentException;
import com.rahgozin.appointment.application.exception.ExceptionEnum;
import com.rahgozin.appointment.application.model.DoctorAppointmentModel;
import com.rahgozin.appointment.application.model.GetAppointmentsRequest;
import com.rahgozin.appointment.application.repository.AppointmentRepository;
import com.rahgozin.appointment.application.repository.DoctorRepository;
import com.rahgozin.appointment.application.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class AppointmentServiceImp implements AppointmentService {


    private final UserRepository userRepository;

    private final AppointmentRepository appointmentRepository;

    private final DoctorRepository doctorRepository;

    @Override
    public List<Appointment> addAppointments(String username, LocalTime startTime, LocalTime endTime, Integer day) {
        int slotDurationInMinutes = 30;
        Optional<User> doctor = userRepository.findByUsername(username);
        List<Appointment> appointments =  generateAppointmentSlots((DoctorEntity) doctor.get(), startTime, endTime, slotDurationInMinutes, day);
        try {
            appointmentRepository.saveAll(appointments);

        }catch (Exception e){
            throw e;
        }
        return appointments;
    }

    @Override
    public List<DoctorAppointmentModel> getAppointments(String username, GetAppointmentsRequest request) {
        Integer date;
        if (request.getDate() == null)
            date = Dateutil.getActionDate(new Date());
        else
            date = request.getDate();
        Optional<User> doctor = userRepository.findByUsername(username);
        List<AppointmentStatus> statuses ;
        if (request.getStatuses() == null)
            statuses = Arrays.stream(AppointmentStatus.values()).toList();
        else
            statuses = request.getStatuses();
        List<Appointment> appointments =  appointmentRepository.findAllByActionDateAndDoctorAndStatusIn(date, (DoctorEntity) doctor.get(), statuses);
        List<DoctorAppointmentModel> appointmentModels = new ArrayList<>();
        for (Appointment appointment : appointments){
            DoctorAppointmentModel appointmentModel = new DoctorAppointmentModel();
            appointmentModel.setAppointment(appointment);
            appointmentModel.setPatient(appointment.getPatient());
            appointmentModels.add(appointmentModel);
        }
        return appointmentModels;


    }

    @Override
    @Transactional
    public List<Appointment> cancelAppointments(List<Long> ids, String username) {
        List<Appointment> appointments = null;
        try {
            appointments = appointmentRepository.findAllById(ids);
            DoctorEntity doctor = doctorRepository.findByUsername(username);
            for(Appointment appointment : appointments){
                if (!doctor.getAppointments().contains(appointment))
                    throw new AppointmentException(ExceptionEnum.APPOINTMENT_DOES_NOT_BELONG_TO_DOCTOR);
                if (appointment.getStatus().equals(AppointmentStatus.RESERVED))
                    throw new AppointmentException(ExceptionEnum.APPOINTMENT_RESERVED);
                appointment.setStatus(AppointmentStatus.CANCELED);
                appointmentRepository.save(appointment);
            }
        }catch (RuntimeException e){
            if (e instanceof ObjectOptimisticLockingFailureException)
                throw new AppointmentException(ExceptionEnum.APPOINTMENT_ALREADY_RESERVED_BY_PATIENT);
        }

        return appointments;
    }


    public static List<Appointment> generateAppointmentSlots(DoctorEntity doctor,LocalTime startTime, LocalTime endTime, int slotDurationInMinutes, Integer day) {

        List<Appointment> appointments = new ArrayList<>();
        while (startTime.plusMinutes(slotDurationInMinutes).isBefore(endTime) || startTime.plusMinutes(slotDurationInMinutes).equals(endTime)) {
            LocalTime slotEnd = startTime.plusMinutes(slotDurationInMinutes);
            Appointment appointment = Appointment.builder().
                    doctor(doctor).
                    date(new Date()).
                    status(AppointmentStatus.EMPTY).
                    startTime(startTime).
                    endTime(slotEnd).
                    actionDate(day).
                    build();
            appointments.add(appointment);
            startTime =slotEnd;
        }
        return appointments;
    }
}
