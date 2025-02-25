package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;

import java.util.List;

public class Query4 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        ShopService shopService = container.select(ShopService.class).get();
        List<Document> averageRatingPerShop = shopService.getAverageRatingPerShop();
        System.out.println("Average Rating Per Shop: ");
        for (Document shop : averageRatingPerShop) {
            System.out.println(shop.toJson());
        }

        container.close();
    }
}
