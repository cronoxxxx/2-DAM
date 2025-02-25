package org.example.domain.service.hibernate;

import jakarta.inject.Inject;
import org.example.dao.hibernate.model.Faction;
import org.example.dao.hibernate.model.Weapon;
import org.example.dao.hibernate.repo.FactionRepoJPA;
import org.example.dao.mongo.model.FactionMB;

import java.time.LocalDate;
import java.util.List;

public class FactionJPAService {
    private final FactionRepoJPA factionRepo;

    @Inject
    public FactionJPAService(FactionRepoJPA factionRepo) {
        this.factionRepo = factionRepo;
    }

    public List<Faction> getAllFactions() {
        return factionRepo.getAll();
    }
    public void addFaction(Faction faction) {
        factionRepo.save(faction);
    }


    public Faction parseFactionFromMongoToJPA(FactionMB factionMB) {
        Faction faction = new Faction();
        faction.setFname(factionMB.getName());
        faction.setContact(factionMB.getContact());
        faction.setPlanet(factionMB.getPlanet());
        faction.setNumberControlledSystems(factionMB.getNumberCS());
        faction.setDateLastPurchase(LocalDate.parse(factionMB.getDateLastPurchase()));
        return faction;
    }

    public List<Weapon> getWeaponsByFaction(String factionName) {
        return factionRepo.getWeaponsByFaction(factionName);
    }

    public void importFactionsFromMongo(List<FactionMB> mongoFactions) {
        List<Faction> factions = mongoFactions.stream().map(this::parseFactionFromMongoToJPA).toList();
        factions.forEach(this::addFaction);
    }


}
