package org.example.ui.hibernate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.bson.Document;
import org.example.dao.hibernate.model.Animal;
import org.example.dao.hibernate.model.AnimalVisit;
import org.example.domain.service.AnimalService;
import org.example.domain.service.AnimalVisitService;

import java.util.List;
import java.util.Scanner;

import org.example.dao.hibernate.jpautil.JPAUtil;

public class Ex2 {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        try (SeContainer container = initializer.initialize()) {
            AnimalService animalService = container.select(AnimalService.class).get();
            AnimalVisitService animalVisitService = container.select(AnimalVisitService.class).get();
            JPAUtil jpaUtil = container.select(JPAUtil.class).get();

            Animal nemo = animalService.findByName("Nemo");
            if (nemo != null) {
                List<AnimalVisit> nemoVisits = animalVisitService.findByAnimalId(nemo.getAnimalId());

                if (!nemoVisits.isEmpty()) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Nemo has visits. want to delete it? (y/n)");
                    String answer = scanner.nextLine().trim().toLowerCase();

                    if (answer.equals("y")) {
                        EntityManager em = jpaUtil.getEntityManager();
                        EntityTransaction tx = em.getTransaction();
                        try {
                            tx.begin();
                            for (AnimalVisit visit : nemoVisits) {
                                Document backupDoc = new Document()
                                        .append("animalId", visit.getAnimal().getAnimalId())
                                        .append("visitorId", visit.getVisitor().getVisitorId())
                                        .append("visitDate", visit.getVisitDate().toString());
                                animalVisitService.backupToMongo(backupDoc);
                                visit = em.merge(visit);
                                em.remove(visit);
                            }
                            nemo = em.merge(nemo);
                            em.remove(nemo);
                            tx.commit();
                            System.out.println("deleted and backed up to MongoDB.");
                        } catch (Exception e) {
                            if (tx.isActive()) {
                                tx.rollback();
                            }
                            e.printStackTrace();
                        } finally {
                            em.close();
                        }
                    } else {
                        System.out.println(" cancelled.");
                    }
                } else {
                    animalService.delete(nemo);
                    System.out.println("Nemodeleted from db.");
                }
            } else {
                System.out.println("Nemo not found .");
            }
        }
    }
}