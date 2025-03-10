package org.example.ui.hibernate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.hibernate.model.Animal;
import org.example.dao.hibernate.model.AnimalVisit;
import org.example.dao.hibernate.model.Habitat;
import org.example.dao.hibernate.model.Visitor;
import org.example.domain.service.AnimalService;
import org.example.domain.service.AnimalVisitService;
import org.example.domain.service.VisitorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ex1 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        try (SeContainer container = initializer.initialize()) {
            AnimalService animalService = container.select(AnimalService.class).get();
            VisitorService visitorService = container.select(VisitorService.class).get();
            AnimalVisitService animalVisitService = container.select(AnimalVisitService.class).get();

            Visitor v = visitorService.findByName("Charlie Green");
            Habitat habitat = visitorService.findHabitat("Savannah");
            LocalDate today = LocalDate.now();
            List<Animal> obtained = animalService.getAllSavannah(habitat);

            for (Animal animal : obtained) {
                AnimalVisit animalVisit = new AnimalVisit();
                animalVisit.setAnimal(animal);
                animalVisit.setVisitor(v);
                animalVisit.setVisitDate(today);
                animalVisitService.save(animalVisit);
            }


            System.out.println(" added successfully.");


        }


    }
}
