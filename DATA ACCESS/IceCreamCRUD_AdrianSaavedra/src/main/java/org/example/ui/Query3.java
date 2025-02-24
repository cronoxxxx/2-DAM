package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.dao.repositories.CustomerDao;
import org.example.domain.service.*;

import java.util.List;

public class Query3 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        CustomerService customerDao = container.select(CustomerService.class).get();

        List<Document> topCustomers = customerDao.getTopCustomersByLoyaltyPoints();
        System.out.println("Top Customers by Loyalty Points: ");
        for (Document customer : topCustomers) {
            System.out.println(customer.toJson());
        }

        container.close();
    }
}
