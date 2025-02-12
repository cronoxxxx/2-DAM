package com.example.hospitalcrud.dao.repositories.jpa.mongo;

import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.model.Payment;
import com.example.hospitalcrud.dao.repositories.PatientRepository;
import com.example.hospitalcrud.dao.repositories.jpa.utils.MongoUtil;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MongoPatientRepository implements PatientRepository {
    private final MongoCollection<Document> collection;

    public MongoPatientRepository() {
        MongoDatabase database = MongoUtil.getDatabase();
        this.collection = database.getCollection("patient");
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        collection.find().into(new ArrayList<>())
                .forEach(doc -> patients.add(documentToPatient(doc)));
        return patients;
    }

    @Override
    public ObjectId add(Patient patient) {
        Document doc = new Document()
                .append("name", patient.getName())
                .append("birthDate", Date.from(patient.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .append("phone", patient.getPhone());

        if (patient.getPayments() != null) {
            List<Document> payments = patient.getPayments().stream()
                    .map(payment -> new Document()
                            .append("amount", payment.getAmount())
                            .append("date", Date.from(payment.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant())))
                    .collect(Collectors.toList());
            doc.append("payments", payments);
        }

        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    @Override
    public void update(Patient patient) {
        collection.updateOne(
                Filters.eq("_id", patient.getId()),
                Updates.combine(
                        Updates.set("name", patient.getName()),
                        Updates.set("birthDate", Date.from(patient.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant())),
                        Updates.set("phone", patient.getPhone())
                )
        );
    }

    @Override
    public Patient getById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return doc != null ? documentToPatient(doc) : null;
    }

    @Override
    public void delete(ObjectId id) {
        collection.deleteOne(Filters.eq("_id", id));
    }

    private Patient documentToPatient(Document doc) {
        Patient patient = new Patient();
        patient.setId(doc.getObjectId("_id"));
        patient.setName(doc.getString("name"));

        Date birthDate = doc.getDate("birthDate");
        if (birthDate != null) {
            patient.setBirthDate(birthDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
        }

        patient.setPhone(doc.getString("phone"));

        List<Document> paymentDocs = doc.getList("payments", Document.class);
        if (paymentDocs != null) {
            patient.setPayments(paymentDocs.stream()
                    .map(payDoc -> {
                        Payment payment = new Payment();
                        payment.setAmount(payDoc.getInteger("amount"));
                        Date paymentDate = payDoc.getDate("date");
                        if (paymentDate != null) {
                            payment.setDate(paymentDate.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate());
                        }
                        return payment;
                    })
                    .collect(Collectors.toList()));
        }

        return patient;
    }
}


