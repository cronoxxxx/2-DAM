package org.example.dao.mappers.jdbc;

import jakarta.inject.Inject;
import org.example.domain.model.Animal;
import org.example.domain.model.Visitor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitorMapper {
    @Inject
    public VisitorMapper() {
    }

    public Visitor mapRow (ResultSet rs) throws SQLException {
        int id = rs.getInt("Visitor_ID");
        String animal_name = rs.getString("Name");
        String species = rs.getString("Email");
        int tickets = rs.getInt("Tickets");
        return new Visitor(id, animal_name, species, tickets);
    }
}
