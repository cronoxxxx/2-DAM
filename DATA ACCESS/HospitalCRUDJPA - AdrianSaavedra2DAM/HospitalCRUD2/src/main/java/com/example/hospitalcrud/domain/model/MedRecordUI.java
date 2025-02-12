package com.example.hospitalcrud.domain.model;

import lombok.*;

import java.util.List;


@Builder
@Data
public class MedRecordUI {

        private int id;
        private String description;
        private String date;
        private int idPatient;
        private int idDoctor;
        private List<String> medications;

        public MedRecordUI(int id, String description, String date, int idPatient, int idDoctor, List<String> medications) {
            this.id = id;
            this.description = description;
            this.date = date;
            this.idPatient = idPatient;
            this.idDoctor = idDoctor;
            this.medications = medications;
        }
    }

