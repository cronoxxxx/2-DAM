package org.example.domain.services;

import jakarta.inject.Inject;
import org.example.dao.jdbc.DaoVisits;
import org.example.domain.model.Visit;

import java.util.List;

public class VisitsService {
    private final DaoVisits daoVisits;
    @Inject
    public VisitsService(DaoVisits daoVisits) {
        this.daoVisits = daoVisits;
    }
    public List<Visit> getAllXML(int id){
      return daoVisits.findAllXML().getAnimalVisits().stream().filter(a -> a.getVisitorID() == id).toList();
    }

    public int save (List<Visit> visitList){
        return daoVisits.saveVisits(visitList);
    }




}
