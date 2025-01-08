package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@AllArgsConstructor
@Data
public class FactionWeapons {
    private Faction faction;
    private List<Weapon> weapons;
}