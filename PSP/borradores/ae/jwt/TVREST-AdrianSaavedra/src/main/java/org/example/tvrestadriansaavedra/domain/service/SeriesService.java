package org.example.tvrestadriansaavedra.domain.service;


import org.example.tvrestadriansaavedra.common.errors.SeriesNotFoundException;
import org.example.tvrestadriansaavedra.common.errors.UnauthorizedOperationException;
import org.example.tvrestadriansaavedra.dao.SeriesDao;
import org.example.tvrestadriansaavedra.domain.model.Series;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {
    private final SeriesDao seriesDao;

    public SeriesService(SeriesDao seriesDao) {
        this.seriesDao = seriesDao;
    }

    public List<Series> getAllSeries() {
        return seriesDao.findAll();
    }

    public Series getSeriesById(Long id) {
        return seriesDao.findById(id)
                .orElseThrow(() -> new SeriesNotFoundException("Series not found with id: " + id));
    }

    public void addSeries(Series series) {
        seriesDao.save(series);
    }

    public void deleteSeries(Long id, String userRole) {
        Series series = getSeriesById(id);
        if ("ADMIN".equals(userRole)) {
            seriesDao.delete(series);
        } else if ("NIVEL1".equals(userRole) || "NIVEL2".equals(userRole)) {
            if (!series.getChapters().isEmpty()) {
                throw new UnauthorizedOperationException("Cannot delete series with chapters");
            }
            seriesDao.delete(series);
        } else {
            throw new UnauthorizedOperationException("Unauthorized to delete series");
        }
    }
}

