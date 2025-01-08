package org.example.services;

import jakarta.inject.Inject;
import org.example.dao.jdbc.DaoFactions;
import org.example.dao.xml.FactionXML;
import org.example.dao.xml.Factions;
import org.example.domain.model.Faction;

import java.util.ArrayList;
import java.util.List;

public class FactionsService {
    private final DaoFactions daoFactions;

    @Inject
    public FactionsService(DaoFactions daoFactions) {
        this.daoFactions = daoFactions;
    }

    public Factions getAllXML() {
        Factions factions = daoFactions.findAllXML();
        return factions != null ? factions : new Factions(new ArrayList<>());
    }

    public List<Faction> getFactions() {
        return daoFactions.getAll();
    }

    public int save(Factions factions) {
        return daoFactions.saveFactionsXML(factions);
    }


}
