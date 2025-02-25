package org.example.ui.mongo;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.mongo.MongoOperationsService;

import java.time.LocalDate;
import java.util.List;

public class Exc2 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 3: Actualizar el nombre de una facción");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        MongoOperationsService factionMongoService = container.select(MongoOperationsService.class).get();

        String oldName = "New order";
        String newName = "Messi";
        factionMongoService.updateFactionName(oldName, newName);
        System.out.println("Nombre de facción actualizado de " + oldName + " a " + newName);

        container.close();
    }
}
