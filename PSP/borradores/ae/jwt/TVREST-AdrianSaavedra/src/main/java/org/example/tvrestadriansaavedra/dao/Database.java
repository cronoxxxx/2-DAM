package org.example.tvrestadriansaavedra.dao;

import lombok.Getter;
import org.example.tvrestadriansaavedra.domain.model.Series;
import org.example.tvrestadriansaavedra.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Getter
@Component
public class Database {
    private long chapterIdCounter = 1;
    private long seriesIdCounter = 1;
    private final List<Series>series;
    private final List<User> users;

    public Database() {
        this.series = new ArrayList<>();
        this.users = new ArrayList<>();
    }



    public long getChapterIdCounter() {
        return chapterIdCounter++;
    }
}
