package com.example.agrirestapi.service;

import com.example.agrirestapi.model.Land;
import com.example.agrirestapi.repository.LandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandService {

    @Autowired
    private LandRepository landRepository;

    public Land createLand(Land land) {
        return landRepository.save(land);
    }

    public List<Land> getAllLands() {
        return landRepository.findAll();
    }

    public Land getLandById(Long id) {
        return landRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Land not found"));
    }

    public List<Land> getLandsWithoutCrops() {
        return landRepository.findLandsWithoutCrops();
    }
}

