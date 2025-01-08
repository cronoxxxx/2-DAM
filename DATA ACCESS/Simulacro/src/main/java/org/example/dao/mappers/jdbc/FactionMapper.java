package org.example.dao.mappers.jdbc;

import jakarta.inject.Inject;
import org.example.domain.model.Faction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FactionMapper {
    @Inject
    public FactionMapper() {}
    public Faction mapRow (ResultSet rs) throws SQLException {
        String fname = rs.getString("fname");
        String contact = rs.getString("contact");
        String planet = rs.getString("planet");
        int number_controlled_systems = rs.getInt("number_controlled_systems");
        LocalDate date_last_purchase = rs.getDate("date_last_purchase").toLocalDate();
        return new Faction(fname,contact,planet,number_controlled_systems,date_last_purchase);

    }
}
