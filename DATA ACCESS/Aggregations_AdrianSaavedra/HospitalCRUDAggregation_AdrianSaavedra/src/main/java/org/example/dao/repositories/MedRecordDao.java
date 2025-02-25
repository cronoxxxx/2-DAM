package org.example.dao.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.dao.repositories.utils.MongoUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class MedRecordDao {
    private final MongoCollection<Document> collection;

    @Inject
    public MedRecordDao(MongoUtil mongoUtil) {
        this.collection = mongoUtil.getDatabase().getCollection("medRecord");
    }

    // En MedRecordDao
    public List<Document> getMedRecordsWithPatientInfo(String patientName) {
        return collection.aggregate(Arrays.asList(
                Aggregates.lookup("patient", "patient", "_id", "patientInfo"),
                Aggregates.unwind("$patientInfo"),
                Aggregates.match(Filters.eq("patientInfo.name", patientName)),
                Aggregates.project(Projections.fields(
                        Projections.include("diagnosis", "date"),
                        Projections.computed("patientName", "$patientInfo.name"),
                        Projections.computed("totalPayment", new Document("$sum", "$patientInfo.payments.amount")),
                        Projections.excludeId()
                ))
        )).into(new ArrayList<>());
    }



    public List<Document> getMedicationCountPerMedRecord() {
        return collection.aggregate(Arrays.asList(
                Aggregates.project(Projections.fields(
                        Projections.include("_id", "medications"),
                        Projections.computed("medicationCount", new Document("$size", "$medications"))
                )),
                Aggregates.project(Projections.fields(
                        Projections.include("medicationCount"),
                        Projections.excludeId()
                ))
        )).into(new ArrayList<>());
    }



    public Document getAverageMedicationsPerMedRecord() {
        return collection.aggregate(Arrays.asList(
                Aggregates.project(Projections.fields(
                        Projections.computed("medicationCount", new Document("$size", "$medications"))
                )),
                Aggregates.group(null,
                        Accumulators.avg("averageMedications", "$medicationCount")),
                Aggregates.project(Projections.fields(
                        Projections.include("averageMedications"),
                        Projections.excludeId()
                ))
        )).first();
    }

    public Document getMostPrescribedMedication() {
        return collection.aggregate(Arrays.asList(
                Aggregates.unwind("$medications"),
                Aggregates.group("$medications",
                        Accumulators.sum("count", 1)),
                Aggregates.sort(Sorts.descending("count")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.computed("medication", "$_id"),
                        Projections.include("count"),
                        Projections.excludeId()
                ))
        )).first();
    }

    public List<Document> getMostPrescribedMedicationPerPatient() {
        return collection.aggregate(Arrays.asList(
                Aggregates.unwind("$medications"),
                Aggregates.group(new Document("patient", "$patient").append("medication", "$medications"),
                        Accumulators.sum("count", 1)),
                Aggregates.sort(Sorts.descending("count")),
                Aggregates.group("$_id.patient",
                        Accumulators.first("medication", "$_id.medication"),
                        Accumulators.first("count", "$count")),
                Aggregates.lookup("patient", "_id", "_id", "patientInfo"),
                Aggregates.unwind("$patientInfo"),
                Aggregates.project(Projections.fields(
                        Projections.include("patientInfo.name", "medication", "count"),
                        Projections.excludeId()
                ))
        )).into(new ArrayList<>());
    }

    public Document getDoctorWithMostPrescriptions() {
        return collection.aggregate(Arrays.asList(
                Aggregates.unwind("$medications"),
                Aggregates.group("$doctor",
                        Accumulators.sum("prescriptionCount", 1)),
                Aggregates.sort(Sorts.descending("prescriptionCount")),
                Aggregates.limit(1),
                Aggregates.lookup("doctor", "_id", "_id", "doctorInfo"),
                Aggregates.unwind("$doctorInfo"),
                Aggregates.project(Projections.fields(
                        Projections.include("doctorInfo.name", "prescriptionCount"),
                        Projections.excludeId()
                ))
        )).first();
    }
}