package org.example.restexamenadriansaavedra.domain.service;

import org.example.restexamenadriansaavedra.common.errors.NotFound;
import org.example.restexamenadriansaavedra.dao.LandRepository;
import org.example.restexamenadriansaavedra.domain.model.Land;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandService {


    private final LandRepository landRepository;

    public LandService(LandRepository landRepository) {
        this.landRepository = landRepository;
    }

    public Land createLand(Land land) {
        return landRepository.save(land);
    }

    public List<Land> getAllLands() {
        return landRepository.findAll();
    }

    public Land getLandById(Long id) {
        return landRepository.findById(id)
                .orElseThrow(() -> new NotFound("Land not found"));
    }

    public List<Land> getLandsWithoutCrops() {
        return landRepository.findLandsWithoutCrops();
    }
}