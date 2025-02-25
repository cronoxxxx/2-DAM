package org.example.ui;

    import jakarta.enterprise.inject.se.SeContainer;
    import jakarta.enterprise.inject.se.SeContainerInitializer;
    import org.bson.Document;
    import org.example.domain.service.MedRecordService;

    import java.util.List;

public class Query4 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        MedRecordService medRecordService = container.select(MedRecordService.class).get();

        // d. Get the number of medications of each medRecord
        List<Document> medicationCounts = medRecordService.getMedicationCountPerMedRecord();
        System.out.println("Medication counts per med record:");
        medicationCounts.forEach(count -> System.out.println(count.toJson()));

        container.close();
    }
}
