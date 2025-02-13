package com.example.hospitalcrud.dao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Credential {
    private ObjectId id;
    private String username;
    private String password;
    private ObjectId patient;
    private ObjectId doctor;
}

