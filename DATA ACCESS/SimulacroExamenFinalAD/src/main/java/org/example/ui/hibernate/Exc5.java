package org.example.ui.hibernate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.hibernate.model.Faction;
import org.example.dao.hibernate.model.Weapon;
import org.example.domain.service.hibernate.FactionJPAService;
import org.example.domain.service.hibernate.WeaponJPAService;

import java.util.List;

public class Exc5 {
    public static void main(String[] args) {
        System.out.println(" Select all factions, and all weapons");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        FactionJPAService factionJPAService = container.select(FactionJPAService.class).get();
        WeaponJPAService weaponJPAService = container.select(WeaponJPAService.class).get();

        List<Faction> factions = factionJPAService.getAllFactions();
        factions.forEach(System.out::println);
        List<Weapon> weapons = weaponJPAService.getWeapons();
        weapons.forEach(System.out::println);

    }
}
