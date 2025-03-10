package org.example.dao.mongo.repo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import org.bson.Document;

import java.util.List;

public class AnimalVisitBackupDAO {
    private final MongoCollection<Document> collection;
    @Inject
    public AnimalVisitBackupDAO(MongoDatabase database) {
        this.collection = database.getCollection("animalvisits");
    }

    public void save(Document document) {
        collection.insertOne(document);
    }

    public Document updateVisit(String visitorName, String animalName, String animalDesc) {
        return collection.findOneAndUpdate(
                new Document("visitor.name", visitorName)
                        .append("date", "2025-02-15"),
                new Document("$push", new Document("animals",
                        new Document("name", animalName)
                                .append("description", animalDesc)
                ))
        );
    }

    public Document sumTotalTicketsElephant(String elephantName, String date) {
        return collection.aggregate(List.of(
                new Document("$match",
                        new Document("date", date)
                                .append("animals.name", elephantName)
                ),
                new Document("$group",
                        new Document("_id", null)
                                .append("totalTickets", new Document("$sum", "$visitor.numTickets"))
                )
        )).first();
    }

    public Document mostVisitedAnimal(){
     return    collection.aggregate(List.of(
                new Document("$unwind", "$animals"),
                new Document("$group",
                    new Document("_id", "$animals.name")
                        .append("visitCount", new Document("$sum", 1))
                ),
                new Document("$sort", new Document("visitCount", -1)),
                new Document("$limit", 1)
            )).first();
    }


}