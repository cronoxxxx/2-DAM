package com.example.hospitalcrud.domain.model;

import lombok.*;

import java.time.LocalDate;

@Data
public class PatientUI {
    private int id,paid;
    private String name,phone,userName,password;
    private LocalDate birthDate;

    public PatientUI(int id, int paid, String name, String phone, String userName, String password, LocalDate birthday) {
        this.id = id;
        this.paid = paid;
        this.name = name;
        this.phone = phone;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthday;
    }


}
