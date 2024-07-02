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
    public List<Appointment> addAppointments(String username, AddAppointmentRequest request) {
        int slotDurationInMinutes = 30;
        Optional<User> doctor = userRepository.findByUsername(username);
        List<Appointment> appointments =  generateAppointmentSlots((DoctorEntity) doctor.get(), request.getStartTime(), request.getEndTime(), slotDurationInMinutes, request.getDay());
        for (Appointment appointment : appointments)
            appointmentRepository.save(appointment);
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


    public static List<Appointment> generateAppointmentSlots(DoctorEntity doctor,String startTime, String endTime, int slotDurationInMinutes, Integer day) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, formatter);

        LocalTime end = LocalTime.parse(endTime, formatter);
        if (end.isBefore(start)){
            throw new AppointmentException(ExceptionEnum.START_TIME_SOONER_THAN_END_TIME);
        }
        List<Appointment> appointments = new ArrayList<>();
        while (start.plusMinutes(slotDurationInMinutes).isBefore(end) || start.plusMinutes(slotDurationInMinutes).equals(end)) {
            LocalTime slotEnd = start.plusMinutes(slotDurationInMinutes);
            Appointment appointment = Appointment.builder().
                    doctor(doctor).
                    date(new Date()).
                    status(AppointmentStatus.EMPTY).
                    startTime(start).
                    endTime(slotEnd).
                    actionDate(day).
                    build();
            appointments.add(appointment);
            start =slotEnd;
        }
        return appointments;
    }
}
