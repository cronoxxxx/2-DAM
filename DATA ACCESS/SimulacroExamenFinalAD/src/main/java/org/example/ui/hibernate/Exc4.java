package org.example.ui.hibernate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.hibernate.model.Weapon;
import org.example.domain.service.hibernate.WeaponJPAService;

public class Exc4 {
    public static void main(String[] args) {
        System.out.println("Update the price of a weapon");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        WeaponJPAService weaponJPAService = container.select(WeaponJPAService.class).get();

        // Find the existing weapon
        Weapon existingWeapon = weaponJPAService.findWeaponByName("Bowcaster");

        if (existingWeapon != null) {
            // Update the price
            existingWeapon.setWprice(1500.0);

            // Perform the update
            weaponJPAService.updateWeapon(existingWeapon);
            System.out.println("Weapon updated successfully");
        } else {
            System.out.println("Weapon not found");
        }
    }
}
