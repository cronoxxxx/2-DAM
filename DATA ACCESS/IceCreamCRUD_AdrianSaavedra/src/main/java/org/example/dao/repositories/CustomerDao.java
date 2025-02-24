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
public class CustomerDao {
    private final MongoCollection<Document> collection;

    @Inject
    public CustomerDao(MongoUtil mongoUtil) {
        this.collection = mongoUtil.getDatabase().getCollection("customers");
    }

    public List<Document> getTopCustomersByLoyaltyPoints() {
        return collection.aggregate(Arrays.asList(
                Aggregates.sort(Sorts.descending("loyaltyPoints")),
                Aggregates.limit(3),
                Aggregates.project(Projections.fields(
                        Projections.include("name", "loyaltyPoints"),
                        Projections.excludeId()
                ))
        )).into(new ArrayList<>());
    }
}