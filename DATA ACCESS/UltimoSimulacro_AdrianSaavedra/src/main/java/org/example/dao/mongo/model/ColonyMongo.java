package org.example.dao.mongo.model;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
public class ColonyMongo {
    private ObjectId id;
    private String name;
    private int population;
    private LocalDate founded;
}
