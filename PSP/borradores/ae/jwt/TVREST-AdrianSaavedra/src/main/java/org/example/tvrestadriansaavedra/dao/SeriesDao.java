package org.example.tvrestadriansaavedra.dao;

import org.example.tvrestadriansaavedra.domain.model.Series;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SeriesDao {
    private final Database database;

    public SeriesDao(Database database) {
        this.database = database;
    }

    public List<Series> findAll() {
        return database.getSeries();
    }

    public Optional<Series> findById(Long id) {
        return database.getSeries().stream()
                .filter(series -> series.getId().equals(id))
                .findFirst();
    }

    public void save(Series series) {
        series.setId(database.getSeriesIdCounter());
        database.getSeries().add(series);
    }

    public void update(Series series) {
        int index = database.getSeries().indexOf(series);
        if (index != -1) {
            database.getSeries().set(index, series);
        }
    }


    public void delete(Series series) {
        database.getSeries().remove(series);
    }
}

