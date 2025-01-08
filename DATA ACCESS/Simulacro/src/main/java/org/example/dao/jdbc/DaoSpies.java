package org.example.dao.jdbc;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.SQLQueries;
import org.example.dao.jdbc.utils.DBConnectionPool;
import org.example.dao.mappers.jdbc.SpyMapper;
import org.example.domain.model.Spy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoSpies {
    private final DBConnectionPool dbConnectionPool;
    private final SpyMapper spyMapper;

    @Inject
    public DaoSpies(SpyMapper spyMapper, DBConnectionPool dbConnectionPool) {
        this.dbConnectionPool = dbConnectionPool;
        this.spyMapper = spyMapper;
    }

    public List<Spy> getAll () {
        List <Spy> spies = new ArrayList<>();
        try (Connection connection = dbConnectionPool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ALL_SPY);
            while (rs.next()) {
                Spy spy = spyMapper.mapRow(rs);
                spies.add(spy);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return spies;
    }

    public int save (Spy spy) {

        try (Connection connection = dbConnectionPool.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(SQLQueries.INSERT_SPY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, spy.getSname());
            ps.setString(2, spy.getSrace());
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


    public boolean deleteSpyAndBattles(int spyId) {
        String deleteBattlesQuery = SQLQueries.DELETE_ID_SPY_BATTLE;
        String deleteSpyQuery = SQLQueries.DELETE_SPY_BY_ID;

        try (Connection connection = dbConnectionPool.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteBattlesStmt = connection.prepareStatement(deleteBattlesQuery);
                 PreparedStatement deleteSpyStmt = connection.prepareStatement(deleteSpyQuery)) {

                deleteBattlesStmt.setInt(1, spyId);
                deleteSpyStmt.setInt(1, spyId);

                int battlesDeleted = deleteBattlesStmt.executeUpdate();
                int spyDeleted = deleteSpyStmt.executeUpdate();

                if (spyDeleted == 0) {
                    connection.rollback();
                    log.warn("Spy with ID {} not found. Operation rolled back.", spyId);
                    return false;
                }

                connection.commit();
                log.info("Deleted spy with ID {}. {} associated battles were deleted.", spyId, battlesDeleted);
                return true;

            } catch (SQLException e) {
                connection.rollback();
                log.error("Error deleting spy and battles for spy ID: " + spyId, e);
                return false;
            }
        } catch (SQLException e) {
            log.error("Error getting database connection", e);
            return false;
        }
    }
}
