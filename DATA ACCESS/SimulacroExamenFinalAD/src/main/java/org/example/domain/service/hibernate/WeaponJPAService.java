package org.example.domain.service.hibernate;

import jakarta.inject.Inject;
import org.example.dao.hibernate.model.Weapon;
import org.example.dao.hibernate.repo.WeaponRepoJPA;
import org.example.dao.mongo.model.FactionMB;
import org.example.dao.mongo.model.WeaponMB;

import java.util.List;

public class WeaponJPAService {
    private final WeaponRepoJPA weaponRepoJPA;
    @Inject
    public WeaponJPAService(WeaponRepoJPA weaponRepoJPA) {
        this.weaponRepoJPA = weaponRepoJPA;
    }

    public List<Weapon> getWeapons() {
        return weaponRepoJPA.getAll();
    }

    public Weapon parseWeaponsFromMongoToJPA(WeaponMB weaponMB){
        Weapon weapon = new Weapon();
        weapon.setWname(weaponMB.getName());
        weapon.setWprice(Double.parseDouble(String.valueOf(weaponMB.getPrice())));
        return weapon;
    }
    public int addWeapon(Weapon weapon){
        return weaponRepoJPA.addWeapon(weapon);
    }
    public void updateWeapon(Weapon weapon){
        weaponRepoJPA.updateWeapon(weapon);
    }

    public Weapon findWeaponByName(String name) {
        return weaponRepoJPA.findByName(name);
    }
    public void deleteWeapon(Weapon weapon) {
        weaponRepoJPA.deleteWeapon(weapon);
    }

    public void importWeaponsFromMongo(List<FactionMB> mongoFactions) {
        mongoFactions.stream()
                .filter(f -> f.getWeapons() != null)
                .flatMap(f -> f.getWeapons().stream())
                .map(this::parseWeaponsFromMongoToJPA)
                .forEach(this::addWeapon);
    }

}

