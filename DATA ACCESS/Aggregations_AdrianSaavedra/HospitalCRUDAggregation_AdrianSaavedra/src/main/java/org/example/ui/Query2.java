package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;
public class Query2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        PatientService patientService = container.select(PatientService.class).get();

        // b. Get the name of the patient who has paid the most
        Document highestPayingPatient = patientService.getPatientWithHighestPayment();
        System.out.println("Highest paying patient: " + highestPayingPatient.toJson());

        container.close();
    }
}
