package com.rahgozin.appointment.application.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient extends User{

    private String name;

    private Long mobileNumber;

    @OneToMany(mappedBy = "patient")
    @JsonBackReference
    private List<Appointment> appointments;

}
