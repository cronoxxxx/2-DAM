package org.example.ui.mongo;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.service.mongo.MongoOperationsService;

public class Exc4 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 5: Eliminar todos los datos de una facción");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        MongoOperationsService factionMongoService = container.select(MongoOperationsService.class).get();

        String factionToDelete = "Empire";
        factionMongoService.deleteFactionData(factionToDelete);
        System.out.println("Datos de la facción " + factionToDelete + " eliminados");

        container.close();
    }
}
