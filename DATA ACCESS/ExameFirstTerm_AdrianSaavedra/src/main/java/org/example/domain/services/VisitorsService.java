package org.example.domain.services;

import jakarta.inject.Inject;
import org.example.dao.jdbc.DaoVisitors;
import org.example.domain.model.Visitor;

import java.util.Objects;

public class VisitorsService {

    private final DaoVisitors daoVisitors;
    @Inject
    public VisitorsService(DaoVisitors daoVisitors) {
        this.daoVisitors = daoVisitors;
    }
    public int save (Visitor visitor){
        return daoVisitors.addVisitorWithVisits(visitor);
    }

    public Visitor getVisitor(String nombre){
        return daoVisitors.getVisitors().stream().filter(visitor -> Objects.equals(visitor.getName(), nombre)).findFirst().orElse(null);
    }
}
