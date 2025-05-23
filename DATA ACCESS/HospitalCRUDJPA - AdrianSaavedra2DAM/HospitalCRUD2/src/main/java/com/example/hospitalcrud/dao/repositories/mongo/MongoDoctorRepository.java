package com.example.hospitalcrud.dao.repositories.mongo;

import com.example.hospitalcrud.dao.model.Doctor;
import com.example.hospitalcrud.dao.repositories.DoctorRepository;
import com.example.hospitalcrud.dao.repositories.utils.MongoUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MongoDoctorRepository implements DoctorRepository {
    private final MongoCollection<Document> collection;

    public MongoDoctorRepository() {
        MongoDatabase database = MongoUtil.getDatabase();
        this.collection = database.getCollection("doctors");
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = new ArrayList<>();
        collection.find().into(new ArrayList<>())
                .forEach(doc -> doctors.add(documentToDoctor(doc)));
        return doctors;
    }

    private Doctor documentToDoctor(Document doc) {
        return Doctor.builder()
                .doctor_id(doc.getObjectId("_id"))
                .name(doc.getString("name"))
                .build();
    }
}

