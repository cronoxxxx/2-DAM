package org.example.ui.mongo;




import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.AnimalVisitService;

public class Exc3 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        try (SeContainer container = initializer.initialize()) {
            AnimalVisitService animalVisitBackupService = container.select(AnimalVisitService.class).get();
            Document updateResult = animalVisitBackupService.updateMongoVisit("Charlie Green","Nemo","Clownfish");
            if (updateResult != null) {
                System.out.println("Charlie visit updated to include Nemo.");
            } else {
                System.out.println("No matching ");
            }


        }
    }
}

