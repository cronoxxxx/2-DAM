package org.example.services;

import jakarta.inject.Inject;
import org.example.dao.jdbc.DaoSpies;
import org.example.dao.jdbc.utils.DBConnectionPool;
import org.example.domain.model.Spy;

import java.util.List;

public class SpiesService {
    private final DaoSpies daoSpies;

    @Inject
    public SpiesService(DaoSpies daoSpies) {
        this.daoSpies = daoSpies;
    }

    public int save(Spy spy) {
        return daoSpies.save(spy);
    }

    public List<Spy> getAll() {
        return daoSpies.getAll();
    }

    public boolean deleteSpyAndBattles(int spyId) {
        return daoSpies.deleteSpyAndBattles(spyId);
    }
}
