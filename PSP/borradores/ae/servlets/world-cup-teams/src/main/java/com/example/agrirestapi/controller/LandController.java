package com.example.agrirestapi.controller;

import com.example.agrirestapi.model.Land;
import com.example.agrirestapi.service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lands")
public class LandController {

    @Autowired
    private LandService landService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Land> createLand(@RequestBody Land land) {
        return ResponseEntity.ok(landService.createLand(land));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEXICANO')")
    public ResponseEntity<List<Land>> getAllLands() {
        List<Land> lands = landService.getAllLands();
        lands.forEach(land -> land.setLandCrops(null)); // Remove landCrops for this response
        return ResponseEntity.ok(lands);
    }

    @GetMapping("/{id}/crops")
    @PreAuthorize("hasRole('MEXICANO')")
    public ResponseEntity<Land> getLandWithCrops(@PathVariable Long id) {
        return ResponseEntity.ok(landService.getLandById(id));
    }

    @GetMapping("/without-crops")
    @PreAuthorize("hasRole('MEXICANO')")
    public ResponseEntity<List<Land>> getLandsWithoutCrops() {
        return ResponseEntity.ok(landService.getLandsWithoutCrops());
    }
}

