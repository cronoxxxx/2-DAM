package org.example.dao.mongo.model;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;
@Data
public class MissionMongo {
    private ObjectId id;
    private String name,objective;
    private LocalDate startDate,endDate;
    private ObjectId spacecraft;
}
