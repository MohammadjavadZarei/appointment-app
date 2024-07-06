package com.rahgozin.appointment.application.service;

import com.rahgozin.appointment.application.Dateutil;
import com.rahgozin.appointment.application.entity.Appointment;
import com.rahgozin.appointment.application.entity.AppointmentStatus;
import com.rahgozin.appointment.application.entity.DoctorEntity;
import com.rahgozin.appointment.application.entity.User;
import com.rahgozin.appointment.application.exception.AppointmentException;
import com.rahgozin.appointment.application.exception.ExceptionEnum;
import com.rahgozin.appointment.application.model.AddAppointmentRequest;
import com.rahgozin.appointment.application.model.DoctorAppointmentModel;
import com.rahgozin.appointment.application.model.GetAppointmentsRequest;
import com.rahgozin.appointment.application.repository.AppointmentRepository;
import com.rahgozin.appointment.application.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class AppointmentServiceImp implements AppointmentService {


    private final UserRepository userRepository;

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> addAppointments(String username, LocalTime startTime, LocalTime endTime, Integer day) {
        int slotDurationInMinutes = 30;
        Optional<User> doctor = userRepository.findByUsername(username);
        List<Appointment> appointments =  generateAppointmentSlots((DoctorEntity) doctor.get(), startTime, endTime, slotDurationInMinutes, day);
        appointmentRepository.saveAll(appointments);
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
