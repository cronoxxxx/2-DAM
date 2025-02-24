package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;

import java.util.List;

public class Query5 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        OrderService orderService = container.select(OrderService.class).get();

        double minAmount = 10.00;
        List<Document> highValueOrders = orderService.getHighValueOrders(minAmount);

        System.out.println("High Value Orders (>" + minAmount + "):");
        for (Document order : highValueOrders) {
            System.out.println(order.toJson());
        }

        container.close();
    }
}
