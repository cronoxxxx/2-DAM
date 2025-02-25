package com.example.agrirestapi.controller;

import com.example.agrirestapi.model.Crop;
import com.example.agrirestapi.model.Land;
import com.example.agrirestapi.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    @Autowired
    private CropService cropService;

    @PostMapping
    @PreAuthorize("hasRole('COLOMBIANO')")
    public ResponseEntity<Crop> createCrop(@RequestBody Crop crop) {
        return ResponseEntity.ok(cropService.createCrop(crop));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COLOMBIANO')")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }

    
        cropService.deleteCrop(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/lands")
    @PreAuthorize("hasRole('COLOMBIANO')")
    public ResponseEntity<List<Land>> getLandsWithCrop(@PathVariable Long id) {
        return ResponseEntity.ok(cropService.getLandsWithCrop(id));
    }
}

