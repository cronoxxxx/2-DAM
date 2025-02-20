package org.example.dao.model;

import lombok.Data;
import lombok.NoArgsConstructor;
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


}

