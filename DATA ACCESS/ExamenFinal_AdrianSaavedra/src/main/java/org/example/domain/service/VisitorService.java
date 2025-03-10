package org.example.domain.service;

import jakarta.inject.Inject;
import org.example.dao.hibernate.model.Habitat;
import org.example.dao.hibernate.model.Visitor;
import org.example.dao.hibernate.repo.VisitorDAO;

public class VisitorService {

    private final VisitorDAO visitorDAO;
    @Inject
    public VisitorService(VisitorDAO visitorDAO) {
        this.visitorDAO = visitorDAO;
    }

    public Visitor findByName(String name) {
        return visitorDAO.findByName(name);
    }

    public Habitat findHabitat(String s){
        return visitorDAO.findByHabitat(s);
    }
}