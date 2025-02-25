package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;

public class Query9 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        MedRecordService medRecordService = container.select(MedRecordService.class).get();

        // i. Get the doctor who prescribes more medications
        Document doctorWithMostPrescriptions = medRecordService.getDoctorWithMostPrescriptions();
        System.out.println("Doctor with most prescriptions: " + doctorWithMostPrescriptions.toJson());

        container.close();
    }
}
