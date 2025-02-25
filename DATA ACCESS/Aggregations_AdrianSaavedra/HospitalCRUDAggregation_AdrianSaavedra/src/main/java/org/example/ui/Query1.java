package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;

public class Query1 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        PatientService patientService = container.select(PatientService.class).get();

        // a. Get the oldest patient
        Document oldestPatient = patientService.getOldestPatient();
        System.out.println("Oldest patient: " + oldestPatient.toJson());

        container.close();
    }
}
