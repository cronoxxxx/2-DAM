package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.model.Animal;
import org.example.domain.model.Visit;
import org.example.domain.model.Visitor;
import org.example.domain.services.AnimalService;
import org.example.domain.services.VisitorsService;
import org.example.domain.services.VisitsService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Tester2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        AnimalService animalService = container.select(AnimalService.class).get();
        VisitsService visitsService = container.select(VisitsService.class).get();
        VisitorsService visitorsService = container.select(VisitorsService.class).get();

        Visitor visitor = new Visitor(0,"David Johnson","david.johnson@example.com",3);
       if ( visitorsService.save(visitor)==1){
           System.out.println("Saved");
           Animal parrot = animalService.getSpecie("Parrot");
           Animal elephant = animalService.getSpecie("Elephant");
           Visitor david = visitorsService.getVisitor("David Johnson");
           Visit visit = new Visit(parrot.getAnimal_id(), david.getVisitor_ID(), LocalDate.now());
           Visit visit2 = new Visit(elephant.getAnimal_id(), david.getVisitor_ID(), LocalDate.now());
           visitsService.save(Arrays.asList(visit,visit2));
       } else {
           System.out.println("Not Saved");
       }
    }
}
