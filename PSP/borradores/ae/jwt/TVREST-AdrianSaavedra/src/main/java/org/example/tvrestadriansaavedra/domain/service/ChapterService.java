package org.example.tvrestadriansaavedra.domain.service;


import org.example.tvrestadriansaavedra.common.errors.ChapterNotFoundException;
import org.example.tvrestadriansaavedra.common.errors.UnauthorizedOperationException;
import org.example.tvrestadriansaavedra.dao.ChapterDao;
import org.example.tvrestadriansaavedra.dao.SeriesDao;
import org.example.tvrestadriansaavedra.domain.model.Chapter;
import org.example.tvrestadriansaavedra.domain.model.Series;
import org.springframework.stereotype.Service;

@Service
public class ChapterService {
    private final ChapterDao chapterDao;
    private final SeriesDao seriesDao;

    public ChapterService(ChapterDao chapterDao, SeriesDao seriesDao) {
        this.chapterDao = chapterDao;
        this.seriesDao = seriesDao;
    }

    public void addChapter(Long seriesId, Chapter chapter) {
        Series series = seriesDao.findById(seriesId).orElseThrow(() -> new ChapterNotFoundException("Series not found with id: " + seriesId));
        chapterDao.save(series, chapter);
        seriesDao.update(series);  // Añade esta línea
    }

    public void updateChapterWatchedStatus(Long seriesId, Long chapterId, boolean watched) {
        Series series = seriesDao.findById(seriesId).orElseThrow(() -> new ChapterNotFoundException("Series not found with id: " + seriesId));
        Chapter chapter = chapterDao.findById(series, chapterId)
                .orElseThrow(() -> new ChapterNotFoundException("Chapter not found with id: " + chapterId));
        chapter.setWatched(watched);
    }

    public void deleteChapter(Long seriesId, Long chapterId, String userRole) {
        Series series = seriesDao.findById(seriesId).orElseThrow(() -> new ChapterNotFoundException("Series not found with id: " + seriesId));
        Chapter chapter = chapterDao.findById(series, chapterId)
                .orElseThrow(() -> new ChapterNotFoundException("Chapter not found with id: " + chapterId));

        if ("ADMIN".equals(userRole)) {
            chapterDao.delete(series, chapter);
        } else if (("NIVEL1".equals(userRole) || "NIVEL2".equals(userRole)) && !chapter.isWatched()) {
            chapterDao.delete(series, chapter);
        } else {
            throw new UnauthorizedOperationException("Unauthorized to delete chapter");
        }
    }
}

