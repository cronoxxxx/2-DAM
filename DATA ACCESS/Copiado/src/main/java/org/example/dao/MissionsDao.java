package org.example.dao;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.model.Mission;
import org.example.model.error.GameError;

import java.util.List;

public class MissionsDao {
    private DBConnectionPool db;

    @Inject
    public MissionsDao(DBConnectionPool db) {
        this.db = db;
    }

    public Either<GameError, Mission> get(int id) {
        JdbcTemplate jtm = new JdbcTemplate(this.db.getDataSource());
        List<Mission> missions = jtm.query("SELECT * FROM Missions WHERE Mission_ID = ?",
                BeanPropertyRowMapper.newInstance(Mission.class), id);
        return missions != null && !missions.isEmpty() ? Either.right(missions.get(0)) : Either.left(new GameError("Mission not found"));
    }
}
