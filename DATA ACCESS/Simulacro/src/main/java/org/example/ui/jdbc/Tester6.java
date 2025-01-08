package org.example.ui.jdbc;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.model.Weapon;
import org.example.services.FactionsService;
import org.example.services.WeaponsService;

import java.util.List;

public class Tester6 {
    public static void main(String[] args) { //no
        var initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        WeaponsService weaponsService = container.select(WeaponsService.class).get();
        FactionsService factionsService = container.select(FactionsService.class).get();

        // ... existing code ...

        System.out.println("5. (0.5) Select the name and the price of all the weapons of a faction.");
        String testFaction = "Empire"; // Replace with an actual faction name from your database
        List<Weapon> factionWeapons = weaponsService.getWeaponsForFaction(testFaction);

        System.out.println("Weapons for faction: " + testFaction);
        for (Weapon weapon : factionWeapons) {
            System.out.println("  - " + weapon.getName() + " (Price: " + weapon.getPrice() + ")");
        }
    }
}
