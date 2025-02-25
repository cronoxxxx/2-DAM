package org.example.restexamenadriansaavedra.dao;

import org.example.restexamenadriansaavedra.domain.model.Crop;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CropRepository {
    private final List<Crop> crops = new ArrayList<>();
    private Long idCounter = 1L;

    public Crop save(Crop crop) {
        crop.setId(idCounter++);
        crop.getLandCrops().forEach(landCrop -> landCrop.setId(idCounter++));
        crop.getLandCrops().forEach(landCrop -> landCrop.getLand().setId(idCounter++));
        crops.add(crop);
        return crop;
    }

    public Optional<Crop> findById(Long id) {
        return crops.stream()
                .filter(crop -> crop.getId().equals(id))
                .findFirst();
    }

    public void delete(Crop crop) {
        crops.remove(crop);
    }
}
