package org.example.restadriansaavedra.domain.service;

import org.example.restadriansaavedra.dao.ReportDao;
import org.example.restadriansaavedra.domain.model.Report;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportDao reportDao;

    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public List<Report> getAllReports() {
        return reportDao.findAll();
    }

    public void addReport(Report report) {
        reportDao.save(report);
    }

    public List<Report> getReportsByUserRole(String role) {
        if ("ADMIN".equals(role)) {
            return getAllReports();
        } else if ("NIVEL2".equals(role)) {
            return reportDao.findAll().stream()
                    .filter(report -> "NIVEL1".equals(report.getSecurityLevel()) || "NIVEL2".equals(report.getSecurityLevel()))
                    .collect(Collectors.toList());
        } else if ("NIVEL1".equals(role)) {
            return reportDao.findAll().stream()
                    .filter(report -> "NIVEL1".equals(report.getSecurityLevel()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public Report getReportByIdAndRole(Long id, String role) {
        Report report = reportDao.findById(id);
        return reportDao.checkAccessAndReturnReport(report, role);
    }
}