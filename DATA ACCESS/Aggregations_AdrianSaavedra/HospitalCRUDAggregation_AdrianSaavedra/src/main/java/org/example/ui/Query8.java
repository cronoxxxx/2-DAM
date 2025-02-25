package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;

import java.util.List;

public class Query8 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        MedRecordService medRecordService = container.select(MedRecordService.class).get();

        // h. Get the most prescribed medication per patient
        List<Document> mostPrescribedPerPatient = medRecordService.getMostPrescribedMedicationPerPatient();
        System.out.println("Most prescribed medication per patient:");
        mostPrescribedPerPatient.forEach(prescription -> System.out.println(prescription.toJson()));

        container.close();
    }
}
