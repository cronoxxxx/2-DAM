package org.example.restexamenadriansaavedra.domain.service;

import org.example.restexamenadriansaavedra.common.errors.CropNotAvailable;
import org.example.restexamenadriansaavedra.dao.CropRepository;
import org.example.restexamenadriansaavedra.domain.model.Crop;
import org.example.restexamenadriansaavedra.domain.model.Land;
import org.example.restexamenadriansaavedra.domain.model.LandCrop;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {


    private final CropRepository cropRepository;
    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public Crop createCrop(Crop crop) {
        return cropRepository.save(crop);
    }

    public void deleteCrop(Long id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new CropNotAvailable("Crop not found"));
            cropRepository.delete(crop);
    }

    public List<Land> getLandsWithCrop(Long cropId) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new CropNotAvailable("Crop not found"));
        return  crop.getLandCrops().stream()
                .map(LandCrop::getLand)
                .toList();
    }
}