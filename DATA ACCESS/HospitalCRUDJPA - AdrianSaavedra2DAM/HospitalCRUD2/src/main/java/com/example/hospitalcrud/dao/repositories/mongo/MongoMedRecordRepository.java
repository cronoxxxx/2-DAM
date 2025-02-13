package com.example.hospitalcrud.dao.repositories.mongo;

import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import com.example.hospitalcrud.dao.repositories.utils.MongoUtil;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.types.*;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MongoMedRecordRepository implements MedRecordRepository {
    private final MongoCollection<Document> collection;

    public MongoMedRecordRepository() {
        MongoDatabase database = MongoUtil.getDatabase();
        this.collection = database.getCollection("medRecord");
    }


    public List<MedRecord> findAll() {
        List<MedRecord> medRecords = new ArrayList<>();
        collection.find().into(new ArrayList<>())
                .forEach(doc -> medRecords.add(documentToMedRecord(doc)));
        return medRecords;
    }


    public ObjectId add(MedRecord medRecord) {
        Document doc = new Document()
                .append("patient", medRecord.getIdPatient())
                .append("doctor", medRecord.getIdDoctor())
                .append("diagnosis", medRecord.getDiagnosis())
                .append("date", Date.from(medRecord.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .append("medications", medRecord.getMedications());

        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }


    public void update(MedRecord medRecord) {
        collection.updateOne(
                Filters.eq("_id", medRecord.getId()),
                Updates.combine(
                        Updates.set("patient", medRecord.getIdPatient()),
                        Updates.set("doctor", medRecord.getIdDoctor()),
                        Updates.set("diagnosis", medRecord.getDiagnosis()),
                        Updates.set("date", Date.from(medRecord.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant())),
                        Updates.set("medications", medRecord.getMedications())
                )
        );
    }


    public List<MedRecord> findByPatientId(ObjectId patientId) {
        List<MedRecord> medRecords = new ArrayList<>();
        collection.find(Filters.eq("patient", patientId)).into(new ArrayList<>())
                .forEach(doc -> medRecords.add(documentToMedRecord(doc)));
        return medRecords;
    }


    public void delete(ObjectId id) {
        collection.deleteOne(Filters.eq("_id", id));
    }


    public void deleteByPatientId(ObjectId patientId) {
        collection.deleteMany(Filters.eq("patient", patientId));
    }

    private MedRecord documentToMedRecord(Document doc) {
        return MedRecord.builder()
                .id(doc.getObjectId("_id"))
                .idPatient(doc.getObjectId("patient"))
                .idDoctor(doc.getObjectId("doctor"))
                .diagnosis(doc.getString("diagnosis"))
                .date(doc.getDate("date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .medications(doc.getList("medications", String.class))
                .build();
    }
}