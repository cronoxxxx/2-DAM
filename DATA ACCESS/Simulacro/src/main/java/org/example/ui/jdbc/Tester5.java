package org.example.ui.jdbc;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.model.FactionWeapons;
import org.example.domain.model.Weapon;
import org.example.services.WeaponsService;

import java.util.List;

public class Tester5 {
    public static void main(String[] args) {
        System.out.println("4. (0.5) Select all factions, and all weapons");
        var initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        WeaponsService weaponsService = container.select(WeaponsService.class).get();

        System.out.println("Using INNER JOIN:");
        List<FactionWeapons> factionWeaponsInnerJoin = weaponsService.getFactionWeaponsWithInnerJoin();
        printFactionWeapons(factionWeaponsInnerJoin);

        System.out.println("\nUsing nested WHERE:");
        List<FactionWeapons> factionWeaponsNestedWhere = weaponsService.getFactionWeaponsWithNestedWhere();
        printFactionWeapons(factionWeaponsNestedWhere);
    }

    private static void printFactionWeapons(List<FactionWeapons> factionWeaponsList) {
        for (FactionWeapons fw : factionWeaponsList) {
            System.out.println("Faction: " + fw.getFaction().getFname());
            System.out.println("Contact: " + fw.getFaction().getContact());
            System.out.println("Planet: " + fw.getFaction().getPlanet());
            System.out.println("Controlled Systems: " + fw.getFaction().getNumber_controlled_systems());
            System.out.println("Last Purchase Date: " + fw.getFaction().getDate_last_purchase());
            System.out.println("Weapons:");
            for (Weapon weapon : fw.getWeapons()) {
                System.out.println("  - " + weapon.getName() + " (ID: " + weapon.getId() + ", Price: " + weapon.getPrice() + ")");
            }
            System.out.println();
        }
    }
}
