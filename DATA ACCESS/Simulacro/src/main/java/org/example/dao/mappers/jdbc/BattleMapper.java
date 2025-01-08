package org.example.dao.mappers.jdbc;

import jakarta.inject.Inject;
import org.example.domain.model.Battle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BattleMapper {
    @Inject
    public BattleMapper() {
    }

    public Battle mapRow (ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String bname = rs.getString("bname");
        String faction_one = rs.getString("faction_one");
        String faction_two = rs.getString("faction_two");
        String bplace = rs.getString("bplace");
        LocalDate bdate  = rs.getDate("bdate").toLocalDate();
        int id_spy = rs.getInt("id_spy");
        return new Battle(id,bname,faction_one,faction_two,bplace,bdate,id_spy);
    }
}
