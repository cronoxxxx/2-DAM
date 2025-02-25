package org.example.domain.service.mongo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.mongo.model.FactionMB;
import org.example.dao.mongo.repo.MongoOperationsDAO;

import java.util.List;
@ApplicationScoped
public class MongoOperationsService {
    private final MongoOperationsDAO factionRepo;
    @Inject
    public MongoOperationsService(MongoOperationsDAO factionRepo) {
        this.factionRepo = factionRepo;
    }
    public List<FactionMB> getAllFactions() {
        return factionRepo.getAll();
    }

    public List<Document> getWeaponsPurchasedByFaction(String factionName, String date) {
        return factionRepo.getWeaponsPurchasedByFaction(factionName, date);
    }

    public void updateFactionName(String oldName, String newName) {
        factionRepo.updateFactionName(oldName, newName);
    }

    public void deleteUnsoldWeapons() {
        factionRepo.deleteUnsoldWeapons();
    }

    public void deleteFactionData(String factionName) {
        factionRepo.deleteFactionData(factionName);
    }
}
