package org.example.domain.service;

import jakarta.inject.Inject;
import org.bson.Document;
import org.example.dao.repositories.FlavorDao;

public class FlavorService {
    private final FlavorDao flavorDao;
    @Inject
    public FlavorService(FlavorDao flavorDao) {
        this.flavorDao = flavorDao;
    }

    public Document getMostExpensiveFlavor() {
        return flavorDao.getMostExpensiveFlavor();
    }
}
