package org.example.restexamenadriansaavedra.ui.controllers;

import org.example.restexamenadriansaavedra.domain.model.Land;
import org.example.restexamenadriansaavedra.domain.service.LandService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lands")
public class LandController {


    private final LandService landService;

    public LandController(LandService landService) {
        this.landService = landService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Land> createLand(@RequestBody Land land) {
        return ResponseEntity.ok(landService.createLand(land));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEXICANO')")
    public ResponseEntity<List<Land>> getAllLands() {
        List<Land> lands = landService.getAllLands();
        return ResponseEntity.ok(lands);
    }

    @GetMapping("/{id}/crops")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEXICANO')")
    public ResponseEntity<Land> getLandWithCrops(@PathVariable Long id) {
        return ResponseEntity.ok(landService.getLandById(id));
    }

    @GetMapping("/without-crops")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEXICANO')")
    public ResponseEntity<List<Land>> getLandsWithoutCrops() {
        return ResponseEntity.ok(landService.getLandsWithoutCrops());
    }
}

