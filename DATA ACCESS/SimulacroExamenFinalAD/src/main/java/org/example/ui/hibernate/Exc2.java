package org.example.ui.hibernate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.hibernate.model.Weapon;
import org.example.domain.service.hibernate.WeaponJPAService;

public class Exc2 {
    public static void main(String[] args) {
        System.out.println("Alice's cousin damaged his brain in a battle. To keep him busy, he is in charge of \n" +
                "recording weapons of the rebels; to avoid mistakes, the program will delete the insertion \n" +
                "immediately.");
        Weapon weapon = new Weapon();
        weapon.setWname("AK47");
        weapon.setWprice(1000.0);
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        WeaponJPAService weaponJPAService = container.select(WeaponJPAService.class).get();

        int i = weaponJPAService.addWeapon(weapon);
        if (i > 0) {
            System.out.println("Added");
            // Immediately delete the weapon
            weaponJPAService.deleteWeapon(weapon);
            System.out.println("Weapon deleted immediately");
        } else {
            System.out.println("Not added");
        }
    }
}