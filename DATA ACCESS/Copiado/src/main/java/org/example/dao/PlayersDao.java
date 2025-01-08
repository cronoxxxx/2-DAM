package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.model.Characters;
import org.example.model.Player;
import org.example.model.PlayerDB;
import org.example.model.error.GameError;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlayersDao {
    private DBConnectionPool db;

    @Inject
    public PlayersDao(DBConnectionPool db) {
        this.db = db;
    }

    public Either<GameError, Player> get(int id) {
        JdbcTemplate jtm = new JdbcTemplate(this.db.getDataSource());
        List<PlayerDB> players = jtm.query("SELECT * FROM Players WHERE Player_ID = ?",
                BeanPropertyRowMapper.newInstance(PlayerDB.class), id);
        if (players != null && !players.isEmpty()) {
            PlayerDB playerDB = players.get(0);
            Player playeraux = new Player();
            playeraux.setId(playerDB.getPlayer_id());
            playeraux.setTokens(playerDB.getTokens());
            return Either.right(playeraux);
        } else {
            return Either.left(new GameError("Player not found"));
        }
    }

    public Either<GameError, Integer> save(Player p) {
        Either<GameError, Integer> result = null;
        int idmax_player = getMaxPlayerId();
        int idmax_character = getMaxCharacterId();

        try (Connection con = this.db.getConnection()) {
            con.setAutoCommit(false);
            try {
                PreparedStatement preparedStatement = con.prepareStatement(
                        "INSERT INTO Players(Player_ID, Name, Email, Tokens) VALUES (?, ?, ?, ?)");
                preparedStatement.setInt(1, ++idmax_player);
                preparedStatement.setString(2, p.getName());
                preparedStatement.setString(3, p.getEmail());
                preparedStatement.setInt(4, p.getTokens());
                int rs = preparedStatement.executeUpdate();
                if (rs == 0) {
                    result = Either.left(new GameError("Error saving player."));
                } else {
                    result = Either.right(0);
                }

                for (Characters c : p.getCharacters()) {
                    preparedStatement = con.prepareStatement(
                            "INSERT INTO Characters(Character_ID, Name, Type, Level, Player_ID) VALUES (?, ?, ?, ?, ?)");
                    preparedStatement.setInt(1, ++idmax_character);
                    preparedStatement.setString(2, c.getName());
                    preparedStatement.setString(3, c.getType());
                    preparedStatement.setInt(4, c.getLevel());
                    preparedStatement.setInt(5, idmax_player);
                    rs = preparedStatement.executeUpdate();
                    if (rs == 0) {
                        result = Either.left(new GameError("Error saving character."));
                        break;
                    }
                }

                if (result.isRight()) {
                    con.commit();
                } else {
                    con.rollback();
                }
            } catch (SQLException e) {
                con.rollback();
                result = Either.left(new GameError("Error connecting database"));
            }
        } catch (SQLException e) {
            result = Either.left(new GameError("Error connecting database"));
        }

        return result;
    }

    public Either<GameError, Integer> update(Player p, int newTokens) {
        Either<GameError, Integer> result = null;
        JdbcTemplate jtm = new JdbcTemplate(this.db.getDataSource());

        try {
            String updateSQL = "UPDATE Players SET Tokens = ? WHERE Player_ID = ?";
            int rowsAffected = jtm.update(updateSQL, newTokens, p.getId());
            if (rowsAffected == 0) {
                result = Either.left(new GameError("Error updating player."));
            } else {
                result = Either.right(0);
            }
        } catch (Exception e) {
            result = Either.left(new GameError("Error connecting database"));
        }

        return result;
    }

    private int getMaxPlayerId() {
        try (Connection con = this.db.getConnection();
             Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery("SELECT MAX(Player_ID) FROM Players")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Error getting max player ID", e);
        }
        return 0;
    }

    private int getMaxCharacterId() {
        try (Connection con = this.db.getConnection();
             Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = stmt.executeQuery("SELECT MAX(Character_ID) FROM Characters")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Error getting max character ID", e);
        }
        return 0;
    }

    private Player readRSPlayer(ResultSet rs) throws SQLException {
        Player result = new Player();
        if (rs.next()) {
            result.setId(rs.getInt("Player_ID"));
            result.setName(rs.getString("Name"));
            result.setEmail(rs.getString("Email"));
            result.setTokens(rs.getInt("Tokens"));
        }
        return result;
    }
}