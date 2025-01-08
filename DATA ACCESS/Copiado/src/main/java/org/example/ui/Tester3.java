package org.example.ui;

import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.model.error.GameError;
import org.example.services.CharactersService;


import java.util.Scanner;

public class Tester3 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        CharactersService charactersService = container.select(CharactersService.class).get();

        Either<GameError, Integer> deletefalse = charactersService.delete(5, false);
        Scanner scanner = new Scanner(System.in);

        if (deletefalse.isRight()) {
            System.out.println(deletefalse.get());
            System.out.println("Deleted!");
        } else {
            GameError error = deletefalse.getLeft();
            if (error.getMessage().equals("The characters has completed missions")) {
                System.out.println("For deleting the customer you have to delete first the completed mission with him, do you want to continue? (Y/N)");
                String answer = scanner.nextLine();
                if (answer.equals("Y")) {
                    Either<GameError, Integer> deletetrue = charactersService.delete(5, true);
                    if (deletetrue.isRight()) {
                        System.out.println(deletetrue.get());
                        System.out.println("Deleted!");
                    } else {
                        System.out.println(deletetrue.getLeft());
                    }
                } else {
                    System.out.println("You answered no.");
                }
            } else {
                System.out.println(error);
            }
        }
    }
}
