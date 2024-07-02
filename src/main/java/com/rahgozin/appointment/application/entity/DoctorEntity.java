package com.rahgozin.appointment.application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorEntity extends User{

    private String name;

    private String address;

    private String email;

    @OneToMany(mappedBy = "doctor")
    @JsonBackReference
    private List<Appointment> appointments;

    private String field;
}
