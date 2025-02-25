package com.example.agrirestapi.repository;

import com.example.agrirestapi.model.Land;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LandRepository {
    private final List<Land> lands = new ArrayList<>();
    private Long idCounter = 1L;

    public Land save(Land land) {
        if (land.getId() == null) {
            land.setId(idCounter++);
        }
        lands.add(land);
        return land;
    }

    public Optional<Land> findById(Long id) {
        return lands.stream()
                .filter(land -> land.getId().equals(id))
                .findFirst();
    }

    public List<Land> findAll() {
        return new ArrayList<>(lands);
    }

    public List<Land> findLandsWithoutCrops() {
        return lands.stream()
                .filter(land -> land.getLandCrops().isEmpty())
                .toList();
    }
}

