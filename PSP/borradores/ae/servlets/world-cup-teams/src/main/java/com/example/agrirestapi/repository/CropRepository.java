package com.example.agrirestapi.repository;

import com.example.agrirestapi.model.Crop;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CropRepository {
    private final List<Crop> crops = new ArrayList<>();
    private Long idCounter = 1L;

    public Crop save(Crop crop) {
        if (crop.getId() == null) {
            crop.setId(idCounter++);
        }
        crops.add(crop);
        return crop;
    }

    public Optional<Crop> findById(Long id) {
        return crops.stream()
                .filter(crop -> crop.getId().equals(id))
                .findFirst();
    }

    public List<Crop> findAll() {
        return new ArrayList<>(crops);
    }

    public void delete(Crop crop) {
        crops.remove(crop);
    }
}

