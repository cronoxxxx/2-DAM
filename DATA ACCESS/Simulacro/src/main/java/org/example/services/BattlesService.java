package org.example.services;

import jakarta.inject.Inject;
import org.example.dao.jdbc.DaoBattles;
import org.example.domain.model.Battle;

import java.util.List;

public class BattlesService {
    private final DaoBattles daoBattles;
    @Inject
    public BattlesService(DaoBattles daoBattles) {
        this.daoBattles = daoBattles;
    }

    public List<Battle> getAll(){
        return daoBattles.getAll();
    }

    public int save (Battle battle){
        return daoBattles.save(battle);
    }
}
