package org.example.domain.service;

import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.hibernate.model.AnimalVisit;
import org.example.dao.hibernate.repo.AnimalVisitDAO;
import org.example.dao.mongo.repo.AnimalVisitBackupDAO;

import java.util.List;

public class AnimalVisitService {

    private final AnimalVisitDAO animalVisitDAO;


    private final AnimalVisitBackupDAO animalVisitBackupDAO;
    @Inject
    public AnimalVisitService(AnimalVisitDAO animalVisitDAO, AnimalVisitBackupDAO animalVisitBackupDAO) {
        this.animalVisitDAO = animalVisitDAO;
        this.animalVisitBackupDAO = animalVisitBackupDAO;
    }

    public void save(AnimalVisit animalVisit) {
        animalVisitDAO.save(animalVisit);
    }

    public List<AnimalVisit> findByAnimalId(Integer animalId) {
        return animalVisitDAO.findByAnimalId(animalId);
    }

    public void backupToMongo(Document document) {
        animalVisitBackupDAO.save(document);
    }

    public Document updateMongoVisit(String visitorName, String animalName, String animalDesc) {
       return animalVisitBackupDAO.updateVisit(visitorName, animalName, animalDesc);
    }

    public Document sumTotalTicketsElephant(String elephant, String date){
        return animalVisitBackupDAO.sumTotalTicketsElephant(elephant, date);
    }

    public Document mostVisitedAnimal(){
        return animalVisitBackupDAO.mostVisitedAnimal();
    }
}