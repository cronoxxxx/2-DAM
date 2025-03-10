package org.example.ui.agg;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.AnimalVisitService;



public class Ex4 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        try (SeContainer container = initializer.initialize()) {
            AnimalVisitService animalVisitService = container.select(AnimalVisitService.class).get();

            //a
            Document result = animalVisitService.sumTotalTicketsElephant("Ellie","2025-02-15");

            if (result != null) {
                System.out.println("Total tickets Feb 15 visitors : " + result.getInteger("totalTickets"));
            } else {
                System.out.println("No resuln.");
            }

          //b
            Document mostVisitedAnimal = animalVisitService.mostVisitedAnimal();

            if (mostVisitedAnimal != null) {
                System.out.println("Most visited animal: " + mostVisitedAnimal.getString("_id"));
            } else {
                System.out.println("No results ");
            }
        }
    }
}
