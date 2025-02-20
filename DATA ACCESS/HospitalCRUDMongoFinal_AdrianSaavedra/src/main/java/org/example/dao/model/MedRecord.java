package org.example.dao.model;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MedRecord {
    private ObjectId id;
    private ObjectId idPatient;
    private ObjectId idDoctor;
    private String diagnosis;
    private LocalDate date;
    private List<String> medications;
}
