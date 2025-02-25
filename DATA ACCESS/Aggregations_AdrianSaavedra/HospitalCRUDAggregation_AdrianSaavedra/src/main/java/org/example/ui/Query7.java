package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;
public class Query7 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        MedRecordService medRecordService = container.select(MedRecordService.class).get();

        // g. Get the medication most prescribed
        Document mostPrescribedMed = medRecordService.getMostPrescribedMedication();
        System.out.println("Most prescribed medication: " + mostPrescribedMed.toJson());

        container.close();
    }
}
