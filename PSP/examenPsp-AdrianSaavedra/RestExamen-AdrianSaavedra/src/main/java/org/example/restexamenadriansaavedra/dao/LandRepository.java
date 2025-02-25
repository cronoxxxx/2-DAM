package org.example.restexamenadriansaavedra.dao;

import org.example.restexamenadriansaavedra.domain.model.Land;
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
            land.getLandCrops().forEach(landCrop -> landCrop.getCrop().setId(idCounter++));
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
        List<Land> example = lands;
         example.forEach(land -> land.setLandCrops(new ArrayList<>()));
         return example;
    }
}
