package org.example.ui.jdbc;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.model.Weapon;
import org.example.services.WeaponsService;

public class Tester4 { //done
    public static void main(String[] args) {
        System.out.println("Update the price of a weapon");



        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        WeaponsService weaponsService = container.select(WeaponsService.class).get();
        var result = weaponsService.updateWeapon(new Weapon(1,"Kaka",8000));
        if (result.isLeft()) {
            System.out.println("Error: " + result.getLeft().getMessage());
        } else {
            System.out.println("Success");
        }
    }

}
