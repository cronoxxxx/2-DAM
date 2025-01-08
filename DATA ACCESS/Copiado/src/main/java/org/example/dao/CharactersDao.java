package org.example.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.config.Configuration;
import org.example.model.CharacterDB;
import org.example.model.CompletedMission;
import org.example.model.error.GameError;



import lombok.extern.log4j.Log4j2;

@Log4j2
public class CharactersDao {
    private DBConnectionPool db;
    private final Configuration config;

    @Inject
    public CharactersDao(DBConnectionPool db, Configuration config) {
        this.db = db;
        this.config = config;
    }

    public Either<GameError, Integer> save(int id) {
        Path file = Paths.get(Configuration.getInstance().getProperty("pathCMissions"));
        List<CompletedMission> completedMissions = this.completedMissionsCharacter(id);
        int playerId = this.characterPlayerId(id);
        Either<GameError, Integer> result = null;
        if (playerId != 0 && completedMissions != null) {
            for (CompletedMission completedMission : completedMissions) {
                String fileData = completedMission.toFileString(playerId);
                try {
                    Files.write(file, fileData.getBytes(), StandardOpenOption.APPEND);
                    result = Either.right(0);
                } catch (IOException e) {
                    result = Either.left(new GameError("Error saving missions"));
                }
            }
        } else {
            result = Either.left(new GameError("Error"));
        }
        return result;
    }

    public Either<GameError, Integer> delete(int id, boolean confirmed) {
        Either<GameError, Integer> result = null;
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(this.db.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            String deleteCharacterSQL = "DELETE FROM Characters WHERE Character_ID = ?";
            if (!confirmed) {
                int rowsAffectedCharacters = jtm.update(deleteCharacterSQL, id);
                if (rowsAffectedCharacters == 0) {
                    transactionManager.rollback(txStatus);
                    result = Either.left(new GameError("Error deleting character"));
                } else {
                    transactionManager.commit(txStatus);
                    result = Either.right(0);
                }
            } else {
                String deleteCompletedMission = "DELETE FROM Completed_Missions WHERE Character_ID = ?";
                int rowsAffectedCMissions = jtm.update(deleteCompletedMission, id);
                int rowsAffectedCharacters = jtm.update(deleteCharacterSQL, id);
                if (rowsAffectedCharacters != 0 && rowsAffectedCMissions != 0) {
                    transactionManager.commit(txStatus);
                    result = Either.right(0);
                } else {
                    transactionManager.rollback(txStatus);
                    result = Either.left(new GameError("Error deleting character"));
                }
            }
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            if (e.getMessage().contains("foreign key constraint fails")) {
                result = Either.left(new GameError("The character has completed missions"));
            } else {
                result = Either.left(new GameError("Error connecting database"));
            }
        }
        return result;
    }

    private List<CompletedMission> completedMissionsCharacter(int id) {
        JdbcTemplate jtm = new JdbcTemplate(this.db.getDataSource());
        return jtm.query("SELECT * FROM Completed_Missions WHERE Character_ID = ?",
                BeanPropertyRowMapper.newInstance(CompletedMission.class), id);
    }

    private int characterPlayerId(int id) {
        JdbcTemplate jtm = new JdbcTemplate(this.db.getDataSource());
        List<CharacterDB> characterDB = jtm.query("SELECT * FROM Characters WHERE Character_ID = ?",
                BeanPropertyRowMapper.newInstance(CharacterDB.class), id);
        return characterDB.get(0).getPlayer_id();
    }
}