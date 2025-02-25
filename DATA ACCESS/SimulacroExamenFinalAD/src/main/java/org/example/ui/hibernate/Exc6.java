package org.example.ui.hibernate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.hibernate.model.Weapon;
import org.example.domain.service.hibernate.FactionJPAService;

import java.util.List;

public class Exc6 {
    public static void main(String[] args) {
        System.out.println("Select the name and the price of all the weapons of a faction.");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        FactionJPAService factionJPAService = container.select(FactionJPAService.class).get();

        String factionName = "Empire"; // Replace with the desired faction name
        List<Weapon> weapons = factionJPAService.getWeaponsByFaction(factionName);
        
        System.out.println("Weapons for faction: " + factionName);
        weapons.forEach(w -> System.out.println("Name: " + w.getWname() + ", Price: " + w.getWprice()));
    }
}