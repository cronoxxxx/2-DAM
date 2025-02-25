package com.example.agrirestapi.service;

import com.example.agrirestapi.model.Crop;
import com.example.agrirestapi.model.Land;
import com.example.agrirestapi.model.LandCrop;
import com.example.agrirestapi.repository.CropRepository;
import com.example.agrirestapi.repository.LandCropRepository;
import com.example.agrirestapi.repository.LandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private LandCropRepository landCropRepository;

    @Autowired
    private LandRepository landRepository;

    public Crop createCrop(Crop crop) {
        return cropRepository.save(crop);
    }

    public void deleteCrop(Long id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        if (landCropRepository.findByCrop(crop).isEmpty()) {
            cropRepository.delete(crop);
        } else {
            throw new RuntimeException("Cannot delete crop as it is associated with lands");
        }
    }

    public List<Land> getLandsWithCrop(Long cropId) {
        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        return landCropRepository.findByCrop(crop).stream()
                .map(LandCrop::getLand)
                .toList();
    }
}

