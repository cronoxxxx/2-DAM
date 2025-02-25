package org.example.ui;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.bson.Document;
import org.example.domain.service.*;

public class Query6 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        MedRecordService medRecordService = container.select(MedRecordService.class).get();

        // f. Get the average number of medications per medRecord
        Document avgMedications = medRecordService.getAverageMedicationsPerMedRecord();
        System.out.println("Average medications per med record: " + avgMedications.toJson());

        container.close();
    }
}
