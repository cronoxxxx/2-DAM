package org.example.dao.mappers.jdbc;

import jakarta.inject.Inject;
import org.example.domain.model.Spy;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SpyMapper {
    @Inject
    public SpyMapper() {
    }

    public Spy mapRow(ResultSet rs) throws SQLException {
       int id = rs.getInt("id");
       String  sname = rs.getString("sname");
       String srace = rs.getString("srace");
        return new Spy(id,sname,srace);
    }
}
