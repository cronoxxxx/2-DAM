package org.example.restadriansaavedra.dao;

import lombok.Getter;
import org.example.restadriansaavedra.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Getter
@Component
public class Database {
    private final List<User> users ;
    private final List<Mouse> mice ;
    private final List<Report> reports ;
    private long reportIdCounter = 1;

    public Database() {
        users = new ArrayList<>();
        mice = new ArrayList<>();
        reports = new ArrayList<>();
    }

    public long getReportIdCounter() {
        return reportIdCounter++;
    }
}