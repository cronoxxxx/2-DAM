package org.example.ui.hibernate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.hibernate.model.Faction;
import org.example.dao.hibernate.model.Weapon;
import org.example.dao.mongo.model.FactionMB;
import org.example.domain.service.hibernate.FactionJPAService;
import org.example.domain.service.hibernate.FactionWeaponJPAService;
import org.example.domain.service.hibernate.WeaponJPAService;
import org.example.domain.service.mongo.MongoOperationsService;

import java.util.List;

public class Exc1 {
    public static void main(String[] args) {
        System.out.println("Load initial data in the database from Mongo...");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        MongoOperationsService mongoOperationsService = container.select(MongoOperationsService.class).get();
        FactionJPAService factionJPAService = container.select(FactionJPAService.class).get();
        WeaponJPAService weaponJPAService = container.select(WeaponJPAService.class).get();
        FactionWeaponJPAService factionWeaponJPAService = container.select(FactionWeaponJPAService.class).get();

        List<FactionMB> mongoFactions = mongoOperationsService.getAllFactions();

        // Import factions
        factionJPAService.importFactionsFromMongo(mongoFactions);

        // Import weapons
        weaponJPAService.importWeaponsFromMongo(mongoFactions);

        // Create cross references
        List<Weapon> weapons = weaponJPAService.getWeapons();
        List<Faction> factions = factionJPAService.getAllFactions();
        factionWeaponJPAService.createCrossReferences(weapons, factions);

        System.out.println("Data import completed.");
    }
}