package com.example.agrirestapi.repository;

import com.example.agrirestapi.model.Crop;
import com.example.agrirestapi.model.Land;
import com.example.agrirestapi.model.LandCrop;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LandCropRepository {
    private final List<LandCrop> landCrops = new ArrayList<>();
    private Long idCounter = 1L;

    public LandCrop save(LandCrop landCrop) {
        if (landCrop.getId() == null) {
            landCrop.setId(idCounter++);
        }
        landCrops.add(landCrop);
        return landCrop;
    }

    public List<LandCrop> findByLand(Land land) {
        return landCrops.stream()
                .filter(lc -> lc.getLand().equals(land))
                .toList();
    }

    public List<LandCrop> findByCrop(Crop crop) {
        return landCrops.stream()
                .filter(lc -> lc.getCrop().equals(crop))
                .toList();
    }

    public void delete(LandCrop landCrop) {
        landCrops.remove(landCrop);
    }
}

