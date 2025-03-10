package org.example.dao.mongo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ResourceMongo {
    private String name,unit;
    private int amount;


}
