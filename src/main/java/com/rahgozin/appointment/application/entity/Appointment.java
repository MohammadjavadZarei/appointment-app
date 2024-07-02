package com.rahgozin.appointment.application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "APPOINTMENT", uniqueConstraints = {@UniqueConstraint(name = "doctor_date_start_end_uq", columnNames = {"start_time", "end_time","date","doctor_id"})})
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer actionDate;

    @Column(name = "DATE")
    @JsonIgnore
    private Date date;

    @Version
    @JsonIgnore
    private int version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonBackReference
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;


    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private LocalTime startTime;

    private LocalTime endTime;
}
