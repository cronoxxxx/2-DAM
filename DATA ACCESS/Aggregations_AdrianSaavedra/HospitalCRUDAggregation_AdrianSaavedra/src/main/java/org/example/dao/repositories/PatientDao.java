package org.example.dao.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.repositories.utils.MongoUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class PatientDao {
    private final MongoCollection<Document> collection;

    @Inject
    public PatientDao(MongoUtil mongoUtil) {
        this.collection = mongoUtil.getDatabase().getCollection("patient");
    }

    public Document getOldestPatient() {
        return collection.aggregate(Arrays.asList(
                Aggregates.sort(Sorts.ascending("birthDate")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.include("name", "birthDate"),
                        Projections.excludeId()
                ))
        )).first();
    }

    public Document getPatientWithHighestPayment() {
        return collection.aggregate(Arrays.asList(
                Aggregates.unwind("$payments"),
                Aggregates.group("$_id",
                        Accumulators.sum("totalPayment", "$payments.amount"),
                        Accumulators.first("name", "$name")),
                Aggregates.sort(Sorts.descending("totalPayment")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.include("name", "totalPayment"),
                        Projections.excludeId()
                ))
        )).first();
    }

    public List<Document> getPatientsWithIbuprofen() {
        return collection.aggregate(Arrays.asList(
                Aggregates.lookup("medRecord", "_id", "patient", "medRecords"),
                Aggregates.unwind("$medRecords"),
                Aggregates.match(Filters.in("medRecords.medications", "Ibuprofen")),
                Aggregates.project(Projections.fields(
                        Projections.include("name"),
                        Projections.excludeId()
                )),
                Aggregates.group("$name",
                        Accumulators.first("name", "$name")),
                Aggregates.project(Projections.fields(
                        Projections.include("name"),
                        Projections.excludeId()
                ))
        )).into(new ArrayList<>());
    }

}