package org.example.dao.mappers.jdbc;

import jakarta.inject.Inject;
import org.example.domain.model.Weapon;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeaponMapper {
    @Inject
    public WeaponMapper() {
    }

    public Weapon mapRow (ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("wname");
        int description = rs.getInt("wprice");
        return new Weapon(id, name, description);
    }
}
