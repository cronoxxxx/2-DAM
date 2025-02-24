package org.example.dao.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import org.bson.Document;
import org.example.dao.repositories.utils.MongoUtil;

import java.util.Arrays;

@ApplicationScoped
public class FlavorDao {
    private final MongoCollection<Document> collection;

    @Inject
    public FlavorDao(MongoUtil mongoUtil) {
        this.collection = mongoUtil.getDatabase().getCollection("flavors");
    }

    public Document getMostExpensiveFlavor() {
        return collection.aggregate(Arrays.asList(
                Aggregates.unwind("$price"),
                Aggregates.sort(Sorts.descending("price.large")),
                Aggregates.limit(1),
                Aggregates.project(Projections.fields(
                        Projections.include("name", "price"),
                        Projections.excludeId()
                ))
        )).first();
    }
}