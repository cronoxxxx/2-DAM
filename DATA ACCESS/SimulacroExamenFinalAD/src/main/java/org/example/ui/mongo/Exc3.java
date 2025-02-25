package org.example.ui.mongo;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.service.mongo.MongoOperationsService;

public class Exc3 {
    public static void main(String[] args) {
        System.out.println("Ejercicio 4: Eliminar armas no vendidas y datos de una facción");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        MongoOperationsService factionMongoService = container.select(MongoOperationsService.class).get();

        // Eliminar armas no vendidas
        factionMongoService.deleteUnsoldWeapons();
        System.out.println("Armas no vendidas eliminadas");
    }
}
