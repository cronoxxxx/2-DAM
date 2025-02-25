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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class ShopDao {
    private final MongoCollection<Document> collection;

    @Inject
    public ShopDao(MongoUtil mongoUtil) {
        this.collection = mongoUtil.getDatabase().getCollection("shops");
    }

    public List<Document> getAverageRatingPerShop() {
        return collection.aggregate(Arrays.asList(
                Aggregates.project(Projections.fields(
                        Projections.include("name", "averageRating"),
                        Projections.excludeId()
                )),
                Aggregates.sort(Sorts.descending("averageRating"))
        )).into(new ArrayList<>());
    }
}