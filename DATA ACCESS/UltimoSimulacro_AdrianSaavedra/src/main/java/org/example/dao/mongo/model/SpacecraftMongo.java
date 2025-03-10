package org.example.dao.mongo.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.example.dao.hibernate.model.Mission;

import java.util.List;
@Data
public class SpacecraftMongo {
    private ObjectId id;
    private String name,type;
    private int capacity;
    private double fuelEfficiency;
    private boolean inService;
    private ObjectId colonyId;

}
