package org.example.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.config.Configuration;
import org.example.model.error.GameError;
import org.example.model.xml.CompletedMissions;
import org.example.model.xml.MissionsXML;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CompletedMissionsDao {
    private final Configuration config;
    private DBConnectionPool db;

    @Inject
    public CompletedMissionsDao(Configuration config, DBConnectionPool db) {
        this.config = config;
        this.db = db;
    }

    public Either<GameError, CompletedMissions> getAll() {
        Path factionsFile = Paths.get(Configuration.getInstance().getProperty("pathXmlCMissions"));
        try {
            JAXBContext context = JAXBContext.newInstance(CompletedMissions.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            CompletedMissions completedMissionsDao = (CompletedMissions) unmarshaller.unmarshal(Files.newInputStream(factionsFile));
            if (completedMissionsDao == null) {
                return Either.left(new GameError("The list of completed missions is empty."));
            } else {
                return Either.right(completedMissionsDao);
            }
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public Either<GameError, Integer> save(CompletedMissions c) {
        Either<GameError, Integer> result = null;
        try (Connection con = this.db.getConnection()) {
            for (MissionsXML missionsXML : c.getCMission()) {
                try (PreparedStatement preparedStatement = con.prepareStatement(
                        "INSERT INTO Completed_Missions(Mission_ID, Character_ID, Completion_Date) VALUES (?, ?, ?)")) {
                    preparedStatement.setInt(1, missionsXML.getId());
                    preparedStatement.setInt(2, c.getPlayer());
                    preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
                    int rs = preparedStatement.executeUpdate();
                    if (rs == 0) {
                        result = Either.left(new GameError("Error saving in database"));
                    } else {
                        result = Either.right(0);
                    }
                }
            }
        } catch (SQLException e) {
            // Handle exception
        }
        return result;
    }

    public Either<GameError, Integer> save(int idMission, int idCharacter) {
        Either<GameError, Integer> result = null;
        JdbcTemplate jtm = new JdbcTemplate(this.db.getDataSource());
        try {
            String saveSQL = "INSERT INTO Completed_Missions(Mission_ID, Character_ID, Completion_Date) VALUES (?, ?, ?)";
            int rowsAffected = jtm.update(saveSQL, idMission, idCharacter, Date.valueOf(LocalDate.now()));
            if (rowsAffected == 0) {
                result = Either.left(new GameError("Error saving mission."));
            } else {
                result = Either.right(0);
            }
        } catch (Exception e) {
            result = Either.left(new GameError("Error connecting database"));
        }
        return result;
    }
}
