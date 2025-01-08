package org.example.ui;


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.model.Visit;
import org.example.domain.services.VisitsService;

import java.util.List;

public class Tester1 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        VisitsService visitsService = container.select(VisitsService.class).get();
        List<Visit> list = visitsService.getAllXML(2);
        System.out.println(list.toString());

       visitsService.save(list);
        System.out.println("Success");
    }
}
