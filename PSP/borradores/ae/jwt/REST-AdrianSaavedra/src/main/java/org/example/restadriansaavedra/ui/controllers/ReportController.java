package org.example.restadriansaavedra.ui.controllers;

import org.example.restadriansaavedra.domain.model.Report;
import org.example.restadriansaavedra.domain.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'NIVEL1', 'NIVEL2')")
    public ResponseEntity<List<Report>> getReports(Authentication authentication) {
        String role = getRole(authentication);
        List<Report> filteredReports = reportService.getReportsByUserRole(role.replace("ROLE_", ""));
        return ResponseEntity.ok(filteredReports);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NIVEL1', 'NIVEL2')")
    public ResponseEntity<Report> getReportById(@PathVariable Long id, Authentication authentication) {
        String role = getRole(authentication);
       Report r = reportService.getReportByIdAndRole(id, role.replace("ROLE_", ""));
        return ResponseEntity.ok(r);

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Report> addReport(@RequestBody Report report) {
        reportService.addReport(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }


    private String getRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");
    }
}