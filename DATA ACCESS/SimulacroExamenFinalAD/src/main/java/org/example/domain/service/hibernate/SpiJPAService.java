package org.example.domain.service.hibernate;

import jakarta.inject.Inject;
import org.example.dao.hibernate.model.Spy;
import org.example.dao.hibernate.repo.SpyRepoJPA;

import java.util.List;

public class SpiJPAService {
    private SpyRepoJPA spyRepoJPA;
    @Inject
    public SpiJPAService(SpyRepoJPA spyRepoJPA) {
        this.spyRepoJPA = spyRepoJPA;
    }

    public List<Spy> getAllSpies() {
        return spyRepoJPA.findAll();
    }
}
