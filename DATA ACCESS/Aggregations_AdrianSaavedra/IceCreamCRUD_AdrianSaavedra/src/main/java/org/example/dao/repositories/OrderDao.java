package org.example.dao.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import org.bson.Document;
import org.example.dao.repositories.utils.MongoUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class OrderDao {
    private final MongoCollection<Document> collection;

    @Inject
    public OrderDao(MongoUtil mongoUtil) {
        this.collection = mongoUtil.getDatabase().getCollection("orders");
    }

    public List<Document> getTotalOrdersPerShop() {
        return collection.aggregate(Arrays.asList(
                Aggregates.group("$shopId", Accumulators.sum("totalOrders", 1)),
                Aggregates.lookup("shops", "_id", "_id", "shopInfo"),
                Aggregates.unwind("$shopInfo"),
                Aggregates.project(Projections.fields(
                        Projections.include("shopInfo.name", "totalOrders"),
                        Projections.excludeId()
                ))
        )).into(new ArrayList<>());
    }

    public List<Document> getHighValueOrders(double minAmount) {
        return collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.gt("totalAmount", minAmount)),
                Aggregates.project(
                        Projections.fields(
                                Projections.include("customerId", "shopId", "totalAmount", "orderDate"),
                                Projections.excludeId()
                        )
                ),
                Aggregates.sort(Sorts.descending("totalAmount"))
        )).into(new ArrayList<>());
    }
}