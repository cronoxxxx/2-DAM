package org.example.tvrestadriansaavedra.ui.controllers;


import org.example.tvrestadriansaavedra.domain.model.Chapter;
import org.example.tvrestadriansaavedra.domain.service.ChapterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/series/{seriesId}/chapters")
public class ChapterController {
    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addChapter(@PathVariable Long seriesId, @RequestBody Chapter chapter) {
        chapterService.addChapter(seriesId, chapter);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{chapterId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NIVEL1', 'NIVEL2')")
    public ResponseEntity<Void> updateChapterWatchedStatus(@PathVariable Long seriesId, @PathVariable Long chapterId, @RequestParam boolean watched) {
        chapterService.updateChapterWatchedStatus(seriesId, chapterId, watched);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{chapterId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'NIVEL1', 'NIVEL2')")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long seriesId, @PathVariable Long chapterId, Authentication authentication) {
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        chapterService.deleteChapter(seriesId, chapterId, role.replace("ROLE_", ""));
        return ResponseEntity.noContent().build();
    }
}

