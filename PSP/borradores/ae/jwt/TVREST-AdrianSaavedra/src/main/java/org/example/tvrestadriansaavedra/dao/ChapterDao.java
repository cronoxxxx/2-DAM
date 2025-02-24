package org.example.tvrestadriansaavedra.dao;

import org.example.tvrestadriansaavedra.domain.model.Chapter;
import org.example.tvrestadriansaavedra.domain.model.Series;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ChapterDao {
    private final Database database;

    public ChapterDao(Database database) {
        this.database = database;
    }

    public void save(Series series, Chapter chapter) {
        chapter.setId(database.getChapterIdCounter());
        series.getChapters().add(chapter);
    }

    public Optional<Chapter> findById(Series series, Long chapterId) {
        return series.getChapters().stream()
                .filter(chapter -> chapter.getId().equals(chapterId))
                .findFirst();
    }

    public void delete(Series series, Chapter chapter) {
        series.getChapters().remove(chapter);
    }
}

