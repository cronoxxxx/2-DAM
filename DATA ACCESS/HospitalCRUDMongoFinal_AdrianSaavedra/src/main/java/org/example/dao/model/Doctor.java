package org.example.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
@Builder
@Data
@AllArgsConstructor
public class Doctor {
    private ObjectId doctor_id;
    private String name;

    public Doctor() {

    }
}
