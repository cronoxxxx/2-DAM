package com.example.hospitalcrud.dao.repositories.jpa.mongo;

import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.repositories.CredentialRepository;
import com.example.hospitalcrud.dao.repositories.jpa.utils.MongoUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public class MongoCredentialRepository implements CredentialRepository {
    private final MongoCollection<Document> collection;

    public MongoCredentialRepository() {
        MongoDatabase database = MongoUtil.getDatabase();
        this.collection = database.getCollection("credential");
    }

    @Override
    public Credential get(String username) {
        Document doc = collection.find(Filters.eq("username", username)).first();
        return doc != null ? documentToCredential(doc) : null;
    }

    @Override
    public void add(Credential credential) {
        Document doc = new Document("username", credential.getUsername())
                .append("password", credential.getPassword())
                .append("patient", credential.getPatient())
                .append("doctor", credential.getDoctor());
        collection.insertOne(doc);
    }


    @Override
    public Credential getByPatientId(ObjectId patientId) {
        Document doc = collection.find(Filters.eq("patient", patientId)).first();
        return doc != null ? documentToCredential(doc) : null;
    }

    @Override
    public void deleteByPatientId(ObjectId patientId) {
        collection.deleteOne(Filters.eq("patient", patientId));
    }

    private Credential documentToCredential(Document doc) {
        Credential credential = new Credential();
        credential.setId(doc.getObjectId("_id"));
        credential.setUsername(doc.getString("username"));
        credential.setPassword(doc.getString("password"));
        credential.setPatient(doc.getObjectId("patient"));
        credential.setDoctor(doc.getObjectId("doctor"));
        return credential;
    }
}

