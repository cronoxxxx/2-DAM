package org.example.restadriansaavedra.ui.controllers;

import org.example.restadriansaavedra.domain.model.Mouse;
import org.example.restadriansaavedra.domain.service.MouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mice")
public class MouseController {
    private final MouseService mouseService;

    public MouseController(MouseService mouseService) {
        this.mouseService = mouseService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'NIVEL1', 'NIVEL2')")
    public ResponseEntity<List<Mouse>> getAllMice() {
        return ResponseEntity.ok(mouseService.getAllMice());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Mouse> addMouse(@RequestBody Mouse mouse) {
        mouseService.addMouse(mouse);
        return ResponseEntity.status(HttpStatus.CREATED).body(mouse);
    }
}