package org.example.dao.mappers.jdbc;

import jakarta.inject.Inject;
import org.example.domain.model.Animal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalMapper {
    @Inject
    public AnimalMapper() {
    }

    public Animal mapRow (ResultSet rs) throws SQLException {
        int id = rs.getInt("Animal_ID");
        String animal_name = rs.getString("Name");
        String species = rs.getString("Species");
        int age = rs.getInt("Age");
        int habitat_id = rs.getInt("Habitat_ID");
        return new Animal(id,animal_name,species,age,habitat_id);
    }
}
