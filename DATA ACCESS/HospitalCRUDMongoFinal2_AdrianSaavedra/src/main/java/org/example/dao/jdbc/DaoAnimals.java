package org.example.dao.jdbc;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.SQLQueries;
import org.example.dao.jdbc.utils.DBConnectionPool;
import org.example.dao.mappers.jdbc.AnimalMapper;
import org.example.domain.model.Animal;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class DaoAnimals {
    private final DBConnectionPool dbConnectionPool;
    private final AnimalMapper animalMapper;
    @Inject
    public DaoAnimals(DBConnectionPool dbConnectionPool, AnimalMapper animalMapper) {
        this.dbConnectionPool = dbConnectionPool;
        this.animalMapper = animalMapper;
    }

    public List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        try (Connection conn = dbConnectionPool.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQLQueries.GET_ALL_ANIMALS);
            while (rs.next()) {
                Animal animal = animalMapper.mapRow(rs);
                animals.add(animal);
            }
        }catch (SQLException e){
            log.error(e.getMessage(),e);
        }
        return animals;

    }

   public int callforDeleteAnimal(int animalId) {
        String sql =SQLQueries.DELETE_ANIMAL_ID;
        String sql2 =SQLQueries.DELETE_ANIMAL_WITH_VISITS;

        try (Connection conn = dbConnectionPool.getConnection();
             PreparedStatement stmt2 = dbConnectionPool.getConnection().prepareStatement(sql2);
             PreparedStatement stmt = dbConnectionPool.getConnection().prepareStatement(sql);) {
            conn.setAutoCommit(false);
            stmt2.setInt(1, animalId);
            stmt2.executeUpdate();
            stmt.setInt(1, animalId);
            stmt.executeUpdate();
            conn.commit();
        }catch (SQLException e){
            log.error(e.getMessage(),e);
        }
        return 0;

   }

    public int count(int animalId) {
        String sql3 = SQLQueries.COUNT_VISITS_BY_ANIMAL_ID;
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement stmt = dbConnectionPool.getConnection().prepareStatement(sql3);) {
            stmt.setInt(1, animalId);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return result.getInt("visitCount");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }



}
