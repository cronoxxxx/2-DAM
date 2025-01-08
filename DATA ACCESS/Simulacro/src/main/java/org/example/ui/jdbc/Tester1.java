package org.example.ui.jdbc;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.services.FactionsService;

public class Tester1 {
    public static void main(String[] args) { //done
        System.out.println("Load initial data in the database from XML, a code that was used in the time of the Great \n" +
                "Creator, when there was only one Light. You can find the XML file within the exam documents. ");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        FactionsService factionsService = container.select(FactionsService.class).get();
        System.out.println(factionsService.getAllXML().toString());
       var result = factionsService.save(factionsService.getAllXML());

       if (result ==1){
           System.out.println("Successfully saved the XML file");
       } else {
           System.out.println("Failed to save the XML file");
       }
    }
}
