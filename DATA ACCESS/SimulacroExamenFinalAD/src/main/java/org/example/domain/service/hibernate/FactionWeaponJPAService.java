package org.example.domain.service.hibernate;

import jakarta.inject.Inject;
import org.example.dao.hibernate.model.Faction;
import org.example.dao.hibernate.model.Weapon;
import org.example.dao.hibernate.model.WeaponsFaction;
import org.example.dao.hibernate.repo.FactionWeaponRepoJPA;

import java.util.List;

public class FactionWeaponJPAService {
    private final FactionWeaponRepoJPA factionWeaponRepoJPA;
    @Inject
    public FactionWeaponJPAService(FactionWeaponRepoJPA factionWeaponRepoJPA) {
        this.factionWeaponRepoJPA = factionWeaponRepoJPA;
    }

    public int addCrossRef(WeaponsFaction weaponsFaction){
        return factionWeaponRepoJPA.addCrossRef(weaponsFaction);
    }

    public void createCrossReferences(List<Weapon> weapons, List<Faction> factions) {
        for (int i = 0; i < Math.min(weapons.size(), factions.size()); i++) {
            WeaponsFaction weaponsFaction = new WeaponsFaction();
            weaponsFaction.setIdWeapon(weapons.get(i));
            weaponsFaction.setNameFaction(factions.get(i));
            addCrossRef(weaponsFaction);
        }
    }
}
