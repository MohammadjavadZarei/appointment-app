package com.rahgozin.appointment.application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "APPOINTMENT", uniqueConstraints = {@UniqueConstraint(name = "doctor_date_start_end_uq", columnNames = {"start_time", "end_time","date","doctor_id"})})
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer actionDate;

    @Column(name = "DATE")
    private Date date;

    @Version
    @JsonIgnore
    private int version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonBackReference
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    private Patient patient;


    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private LocalTime startTime;

    private LocalTime endTime;
}
