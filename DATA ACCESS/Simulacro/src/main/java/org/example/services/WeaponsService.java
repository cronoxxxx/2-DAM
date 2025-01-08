package org.example.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.jdbc.DaoWeapons;
import org.example.domain.errors.FactionsError;
import org.example.domain.model.FactionWeapons;
import org.example.domain.model.Weapon;

import java.util.List;

public class WeaponsService {
    private final DaoWeapons daoWeapons;

    @Inject
    public WeaponsService(DaoWeapons daoWeapons) {
        this.daoWeapons = daoWeapons;
    }

    public Either<FactionsError, Integer> updateWeapon(Weapon weapon) {
        return daoWeapons.updateWeapon(weapon);
    }

    public int saveWeapons (Weapon weapon){
        var a = daoWeapons.getAllWeapons().stream().filter( w -> w.getName().equals(weapon.getName())).toList();
        if (!a.isEmpty()){
            return 0;
        }
        return daoWeapons.saveWeapon(weapon);
    }

    public List<FactionWeapons> getFactionWeaponsWithInnerJoin() {
        return daoWeapons.getFactionWeaponsWithInnerJoin();
    }

    public List<FactionWeapons> getFactionWeaponsWithNestedWhere() {
        return daoWeapons.getFactionWeaponsWithNestedWhere();
    }

    public List<Weapon> getWeaponsForFaction(String factionName) {
        return daoWeapons.getWeaponsForFaction(factionName);
    }
}
