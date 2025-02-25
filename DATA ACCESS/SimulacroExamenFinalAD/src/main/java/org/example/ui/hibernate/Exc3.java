package org.example.ui.hibernate;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.dao.hibernate.model.Battle;
import org.example.dao.hibernate.model.Faction;
import org.example.dao.hibernate.model.Spy;
import org.example.domain.service.hibernate.*;

import java.time.LocalDate;

public class Exc3 {
    public static void main(String[] args) {
        System.out.println(" Insert a new battle, inserting faction if it does not exist.  ");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        FactionJPAService factionJPAService = container.select(FactionJPAService.class).get();
        BattleJPAService battleJPAService = container.select(BattleJPAService.class).get();
        SpiJPAService spyJPAService = container.select(SpiJPAService.class).get();
        Faction faction = factionJPAService.getAllFactions().stream().findAny().get();
        Faction faction2 = factionJPAService.getAllFactions().stream().findAny().get();
        Spy spy = spyJPAService.getAllSpies().stream().findAny().get();
        Battle battle = new Battle();
        battle.setBname("aaaaa");
        battle.setBplace("Space");
        battle.setBdate(LocalDate.of(2020, 1, 1));
        battle.setFactionOne(faction);
        battle.setFactionTwo(faction2);
        battle.setSpy(spy);
        int i = battleJPAService.addBattle(battle);
        if (i > 0) {
            System.out.println("Added battle " + battle.getBname());
        } else {
            System.out.println("Battle not added");
        }


    }
}
