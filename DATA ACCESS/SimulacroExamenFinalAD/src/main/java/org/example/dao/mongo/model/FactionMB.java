package org.example.dao.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FactionMB {
    private ObjectId id;
    private String name;
    private String contact;
    private String planet;
    private int numberCS;
    private String dateLastPurchase;
    private List<WeaponMB> weapons;
}
