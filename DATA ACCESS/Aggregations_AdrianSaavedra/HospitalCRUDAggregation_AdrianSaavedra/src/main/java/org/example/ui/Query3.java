package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;

import java.util.List;

public class Query3 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        MedRecordService medRecordService = container.select(MedRecordService.class).get();

        // c. Get the medRecords of a given patient, showing the name of the patient and the total payment
        List<Document> patientMedRecords = medRecordService.getMedRecordsWithPatientInfo("Jane Smith");
        System.out.println("Med records:");
        patientMedRecords.forEach(r -> System.out.println(r.toJson()));

        container.close();
    }
}
