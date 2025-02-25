package org.example.ui.mongo;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.mongo.MongoOperationsService;

import java.time.LocalDate;
import java.util.List;

public class Exc1 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 1: Cargar armas compradas por faccion");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        MongoOperationsService factionMongoService = container.select(MongoOperationsService.class).get();


        String factionName = "New order";
        String date = LocalDate.now().minusYears(500).toString();
        List<Document> weapons = factionMongoService.getWeaponsPurchasedByFaction(factionName, date);
        System.out.println("Armas compradas por " + factionName + " desde " + date + ":");
        weapons.forEach(System.out::println);
    }
}
