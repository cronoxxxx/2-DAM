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

        PatientService patientService = container.select(PatientService.class).get();

        // e. Get the name of the patients who have been prescribed Ibuprofen
        List<Document> patientsWithIbuprofen = patientService.getPatientsWithIbuprofen();
        System.out.println("Patients prescribed Ibuprofen:");
        patientsWithIbuprofen.forEach(patient -> System.out.println(patient.toJson()));

        container.close();
    }
}
