package org.example.ui.jdbc;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.xml.FactionXML;
import org.example.dao.xml.Factions;
import org.example.domain.model.Battle;
import org.example.domain.model.Faction;
import org.example.domain.model.Spy;
import org.example.services.BattlesService;
import org.example.services.FactionsService;
import org.example.services.SpiesService;

import java.time.LocalDate;
import java.util.List;


public class Tester3 {
    public static void main(String[] args) {
        System.out.println("Insert a new battle, inserting faction if it does not exist.");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        SeContainer container = initializer.initialize();
        SpiesService spiesService = container.select(SpiesService.class).get();
        BattlesService battlesService = container.select(BattlesService.class).get();
//first add spy : done

        var valueSpy = spiesService.save(new Spy(0, "John", "Ca"));
        if (valueSpy == 1) {
            System.out.println("Se ha añadido");
        } else {
            System.out.println("Error");
        }

        Spy s = spiesService.getAll().stream().findAny().get();

//then add two factions
        FactionsService factionsService = container.select(FactionsService.class).get();
        var result = factionsService.getFactions();
        Faction faction1 = result.stream().findAny().get();
        Faction faction2 = result.stream().findAny().get();


//add them in a new battle object
        var valueBattle = battlesService.save(new Battle(0, "Yavin2", faction1.getFname(), faction2.getFname(), "Space", LocalDate.of(2020, 1, 1), s.getId()));
        if (valueBattle == 1) {
            System.out.println("Se ha guardado la batalla");
        } else {
            System.out.println("Error");
        }

        System.out.println(battlesService.getAll());


    }
}
