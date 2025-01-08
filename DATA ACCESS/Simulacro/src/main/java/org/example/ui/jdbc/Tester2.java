package org.example.ui.jdbc;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.xml.FactionXML;
import org.example.dao.xml.Factions;
import org.example.dao.xml.WeaponXML;
import org.example.domain.model.Weapon;
import org.example.services.FactionsService;
import org.example.services.WeaponsService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tester2 {
    public static void main(String[] args) {
        System.out.println(" Alice's cousin damaged his brain in a --battle. To keep him busy, he is in charge of \n" +
                "recording weapons of the rebels; to avoid mistakes, the program will delete the insertion \n" +
                "immediately. ");

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        WeaponsService weaponsService = container.select(WeaponsService.class).get();
        Weapon weapon = new Weapon(0, "AK47", 1000);
        var result = weaponsService.saveWeapons(weapon);
        if (result == 1) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
        }


    }

}
