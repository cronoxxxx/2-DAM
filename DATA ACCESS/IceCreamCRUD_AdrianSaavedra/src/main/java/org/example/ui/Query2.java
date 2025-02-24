package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;

import java.util.List;

public class Query2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        OrderService orderService = container.select(OrderService.class).get();
        List<Document> totalOrdersPerShop = orderService.getTotalOrdersPerShop();
        System.out.println("Total Orders Per Shop: " );
        for (Document order : totalOrdersPerShop) {
            System.out.println(order.toJson());
        }

        container.close();
    }
}
