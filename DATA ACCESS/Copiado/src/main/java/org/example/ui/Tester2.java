package org.example.ui;

import io.vavr.control.Either;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.model.Characters;
import org.example.model.Player;
import org.example.model.error.GameError;
import org.example.services.PlayersService;


import java.util.ArrayList;
import java.util.List;

public class Tester2 {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        PlayersService playersService = container.select(PlayersService.class).get();

        List<Characters> characters = new ArrayList<>();
        characters.add(new Characters(0, "Mr White", "Hobbit", 25));
        characters.add(new Characters(0, "Mr Black", "Elf", 17));

        Player anabel = new Player(0, "Anabel", "anabel@anabel.com", 200, characters);
        Either<GameError, Integer> eitherSave = playersService.save(anabel);

        if (eitherSave.isRight()) {
            System.out.println(eitherSave.get());
            System.out.println("Saved");
        } else {
            System.out.println(eitherSave.getLeft());
        }
    }
}
