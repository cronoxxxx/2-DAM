package org.example.ui;

import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.CompletedMissionsDao;
import org.example.model.error.GameError;
import org.example.model.xml.CompletedMissions;
import org.example.services.CompletedMissionsService;


public class Tester1 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        CompletedMissionsService completedMissionsService = container.select(CompletedMissionsService.class).get();

        Either<GameError, CompletedMissions> eitherGetAll = completedMissionsService.getAll();
        if (eitherGetAll.isRight()) {
            System.out.println(eitherGetAll.get());
            Either<GameError, Integer> eitherSave = completedMissionsService.save(eitherGetAll.get());
            if (eitherSave.isRight()) {
                System.out.println(eitherSave.get());
            } else {
                System.out.println(eitherSave.getLeft());
            }
        } else {
            System.out.println(eitherGetAll.getLeft());
        }
    }
}
