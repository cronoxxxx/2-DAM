package org.example.dao.repositories.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static final String CONNECTION_STRING = "mongodb://informatica.iesquevedo.es:2323";
    private static final String DATABASE_NAME = "adriansaavedra_hospital";
    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                    .build();
            mongoClient = MongoClients.create(settings);
        }
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}




