package org.example.dao.mongo.model;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;
@Data
public class AstronautMongo {
    private ObjectId id;
    private String name;
    private String rank,specialization;
    private List<MissionMongo> missions;
}
