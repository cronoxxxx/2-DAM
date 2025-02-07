package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class Patient {
    private ObjectId id;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private List<Payment> payments;

    @Data
    @NoArgsConstructor
    public static class Payment {
        private double amount;
        private LocalDate date;
    }
}

