package org.example.dao.jdbc;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.SQLQueries;
import org.example.dao.jdbc.utils.DBConnectionPool;
import org.example.dao.mappers.jdbc.BattleMapper;
import org.example.domain.model.Battle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoBattles {
    private final DBConnectionPool dbConnectionPool;
    private final BattleMapper battleMapper;

    @Inject
    public DaoBattles(DBConnectionPool dbConnectionPool, BattleMapper battleMapper) {
        this.battleMapper = battleMapper;
        this.dbConnectionPool = dbConnectionPool;
    }

    public List<Battle> getAll() {
        List<Battle> battles = new ArrayList<>();
        try (Connection connection = dbConnectionPool.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ALL_BATTLE);
            while (rs.next()) {
                Battle battle = battleMapper.mapRow(rs);
                battles.add(battle);
            }
        }catch (SQLException e){
            log.error(e.getMessage(),e);
        }
        return battles;
    }



    public int save(Battle battle) {
        try (Connection connection = dbConnectionPool.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(SQLQueries.INSERT_BATTLE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, battle.getBname());
            ps.setString(2, battle.getFaction_one());
            ps.setString(3, battle.getFaction_two());
            ps.setString(4, battle.getBplace());
            ps.setDate(5, Date.valueOf(battle.getBdate()));
            ps.setInt(6,battle.getId_spy());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                connection.rollback();
            }
            connection.commit();
            return 1;


        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return 0;


    }
}
