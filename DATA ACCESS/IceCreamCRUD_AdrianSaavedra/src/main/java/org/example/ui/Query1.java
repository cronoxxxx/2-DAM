package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;

public class Query1 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        FlavorService flavorService = container.select(FlavorService.class).get();

        Document mostExpensiveFlavor = flavorService.getMostExpensiveFlavor();
        System.out.println("Most Expensive Flavor: " + mostExpensiveFlavor.toJson());

        container.close();
    }
}