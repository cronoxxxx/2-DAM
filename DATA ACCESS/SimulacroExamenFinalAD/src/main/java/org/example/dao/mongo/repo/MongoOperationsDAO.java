package org.example.dao.mongo.repo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.mongo.model.FactionMB;
import org.example.dao.mongo.model.WeaponMB;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MongoOperationsDAO {
    private final MongoCollection<Document> collection;

    @Inject
    public MongoOperationsDAO(MongoDatabase database) {
        this.collection = database.getCollection("factions");
    }


    public List<FactionMB> getAll() {
        List<FactionMB> factions = new ArrayList<>();
        collection.find().into(new ArrayList<>()).forEach(faction -> factions.add(documentToFaction(faction)));
        return factions;
    }

    public void updateFactionName(String oldName, String newName) {
        collection.updateMany(
                Filters.eq("name", oldName),
                Updates.set("name", newName));
    }

    public void deleteFactionData(String factionName) {
        collection.deleteOne(Filters.eq("name", factionName));
    }

    private FactionMB documentToFaction(Document document) {
        FactionMB faction = new FactionMB();
        faction.setId(document.getObjectId("_id"));
        faction.setName(document.getString("name"));
        faction.setContact(document.getString("contact"));
        faction.setPlanet(document.getString("planet"));
        faction.setNumberCS(document.getInteger("numberCS"));
        faction.setDateLastPurchase(document.getString("dateLastPurchase"));

        List<Document> weapons = document.getList("weapons", Document.class);
        if (weapons != null) {
            faction.setWeapons(weapons.stream().map(
                    document1 -> {
                        WeaponMB weapon = new WeaponMB();
                        weapon.setName(document1.getString("name"));
                        weapon.setPrice(document1.getInteger("price"));
                        return weapon;
                    }
            ).toList());
        }
        return faction;
    }

    public void deleteUnsoldWeapons() {
        collection.updateMany(
                Filters.exists("weapons"),
                Updates.pull("weapons", Filters.eq("price", 0))
        );
    }

    public List<Document> getWeaponsPurchasedByFaction(String factionName, String date) {
        return collection.find(
                Filters.and(
                        Filters.eq("name", factionName),
                        Filters.gte("dateLastPurchase", date)
                )
        ).into(new ArrayList<>());
    }


}
