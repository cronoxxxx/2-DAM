package org.example.dao.repositories.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.repositories.utils.MongoUtil;

public class PatientRepository {
    private final MongoCollection<Document> collection;
    @Inject
    public PatientRepository() {
        MongoDatabase database = MongoUtil.getDatabase();
        this.collection = database.getCollection("patient");
    }
}
