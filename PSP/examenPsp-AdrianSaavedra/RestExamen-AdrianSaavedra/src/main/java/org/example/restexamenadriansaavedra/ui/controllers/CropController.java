package org.example.restexamenadriansaavedra.ui.controllers;


import org.example.restexamenadriansaavedra.domain.model.Crop;
import org.example.restexamenadriansaavedra.domain.model.Land;
import org.example.restexamenadriansaavedra.domain.service.CropService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crops")
public class CropController {


    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

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

    @GetMapping("/{id}/lands")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEXICANO')")
    public ResponseEntity<List<Land>> getLandsWithCrop(@PathVariable Long id) {
        return ResponseEntity.ok(cropService.getLandsWithCrop(id));
    }
}

