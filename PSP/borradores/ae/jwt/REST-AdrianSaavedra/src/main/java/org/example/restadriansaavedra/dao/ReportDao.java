package org.example.restadriansaavedra.dao;

import org.example.restadriansaavedra.common.errors.ReportNotFoundException;
import org.example.restadriansaavedra.common.errors.RoleNotValidException;
import org.example.restadriansaavedra.domain.model.Report;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReportDao {
    private final Database database;

    public ReportDao(Database database) {
        this.database = database;
    }

    public List<Report> findAll() {
        return database.getReports();
    }

    public Report findById(Long id) {
        return database.getReports().stream()
                .filter(report -> report.getId().equals(id))
                .findFirst().orElseThrow(() -> new ReportNotFoundException("Report not found"));
    }

    public void save(Report report) {
        report.setId(database.getReportIdCounter());
        database.getReports().add(report);
    }

    public Report checkAccessAndReturnReport(Report report, String role) {
        if ("ADMIN".equals(role) || "NIVEL2".equals(role) ||
                ("NIVEL1".equals(role) && "NIVEL1".equals(report.getSecurityLevel()))) {
            return report;
        }
        throw new RoleNotValidException("Access denied for this report");
    }
}