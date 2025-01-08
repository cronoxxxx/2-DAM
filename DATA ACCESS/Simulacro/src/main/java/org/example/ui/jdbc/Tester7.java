package org.example.ui.jdbc;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.services.FactionsService;
import org.example.services.SpiesService;
import org.example.services.WeaponsService;

public class Tester7 {
    public static void main(String[] args) { //no
        System.out.println("6. (0.75) Delete the data of a spy and his/her battles, so that if something happens to them, " +
                "they can deny their existence.");

        var initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        WeaponsService weaponsService = container.select(WeaponsService.class).get();
        FactionsService factionsService = container.select(FactionsService.class).get();
        SpiesService spiesService = container.select(SpiesService.class).get();

        int spyIdToDelete = 1; // Replace with an actual spy ID from your database
        boolean deletionSuccessful = spiesService.deleteSpyAndBattles(spyIdToDelete);

        if (deletionSuccessful) {
            System.out.println("Spy with ID " + spyIdToDelete + " and their associated battles have been successfully deleted.");
        } else {
            System.out.println("Failed to delete spy with ID " + spyIdToDelete + " and their associated battles.");
        }

    }
}
