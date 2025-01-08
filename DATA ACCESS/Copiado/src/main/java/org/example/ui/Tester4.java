package org.example.ui;

import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

import org.example.model.error.GameError;
import org.example.services.CompletedMissionsService;

public class Tester4 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        CompletedMissionsService completedMissionsService = container.select(CompletedMissionsService.class).get();

        Either<GameError, Integer> eitherSave = completedMissionsService.save(3, 4, 5);
        if (eitherSave.isRight()) {
            System.out.println(eitherSave.get());
            System.out.println("Saved");
        } else {
            System.out.println(eitherSave.getLeft());
        }
    }
}
