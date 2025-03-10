package org.example.dao.mongo.repo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.mongo.model.AstronautMongo;
import org.example.dao.mongo.model.MissionMongo;
import org.example.dao.mongo.model.ResourceMongo;

import java.util.ArrayList;
import java.util.List;

public class MongoOperationsDAO {
    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> collection2;
    private final MongoCollection<Document> collection3;
    private final MongoCollection<Document> collection4;

    @Inject
    public MongoOperationsDAO(MongoDatabase database) {
        this.collection = database.getCollection("astronauts");
        this.collection2 = database.getCollection("colonies");
        this.collection3 = database.getCollection("missions");
        this.collection4 = database.getCollection("spacecraft");

    }

    public List<AstronautMongo> findAll() {
        List<AstronautMongo> astronauts = new ArrayList<>();
        collection.find().into(new ArrayList<>()).forEach(astronaut ->);
    }

    public AstronautMongo documentToAstronaut(Document document) {
        AstronautMongo astronautMongo = new AstronautMongo();
        astronautMongo.setId(document.getObjectId("_id"));
        astronautMongo.setName(document.getString("name"));
        astronautMongo.setRank(document.getString("rank"));
        astronautMongo.setSpecialization(document.getString("specialization"));

        List<Document> missions = document.getList("missions", Document.class);
        if (missions != null) {
            astronautMongo.setMissions(missions.stream().map(
                    document1 -> {
                        MissionMongo resourceMongo = new MissionMongo();
                        resourceMongo.setId( document1.getObjectId("_id"));
                        resourceMongo.setName(document1.getString("name"));
                        resourceMongo.setObjective(document1.getString("objective"));
                        resourceMongo.setStartDate(document1.getDate());
                        return resourceMongo;
                    }
            ).toList());

        }
    }


}
