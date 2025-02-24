package org.example.tvrestadriansaavedra.ui.controllers;


import org.example.tvrestadriansaavedra.domain.model.Series;
import org.example.tvrestadriansaavedra.domain.service.SeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SeriesController {
    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'NIVEL1', 'NIVEL2')")
    public ResponseEntity<List<Series>> getAllSeries() {
        return ResponseEntity.ok(seriesService.getAllSeries());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NIVEL1', 'NIVEL2')")
    public ResponseEntity<Series> getSeriesById(@PathVariable Long id) {
        return ResponseEntity.ok(seriesService.getSeriesById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'NIVEL1', 'NIVEL2')")
    public ResponseEntity<Series> addSeries(@RequestBody Series series) {
        seriesService.addSeries(series);
        return ResponseEntity.status(HttpStatus.CREATED).body(series);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NIVEL1', 'NIVEL2')")
    public ResponseEntity<Void> deleteSeries(@PathVariable Long id, Authentication authentication) {
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        seriesService.deleteSeries(id, role.replace("ROLE_", ""));
        return ResponseEntity.noContent().build();
    }
}

