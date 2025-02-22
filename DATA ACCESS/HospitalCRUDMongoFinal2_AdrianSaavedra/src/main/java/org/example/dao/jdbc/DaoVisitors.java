package org.example.dao.jdbc;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.SQLQueries;
import org.example.dao.jdbc.utils.DBConnectionPool;
import org.example.dao.mappers.jdbc.VisitorMapper;
import org.example.domain.model.Animal;
import org.example.domain.model.Visitor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoVisitors {
    private final DBConnectionPool dbConnectionPool;
    private final VisitorMapper visitorMapper;

    @Inject
    public DaoVisitors(DBConnectionPool dbConnectionPool, VisitorMapper visitorMapper) {
        this.dbConnectionPool = dbConnectionPool;
        this.visitorMapper = visitorMapper;
    }

    public List<Visitor> getVisitors() {
        List<Visitor> visitors = new ArrayList<>();
        try (Connection conn = dbConnectionPool.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQLQueries.GET_ALL_VISITORS);
            while (rs.next()) {
                Visitor animal = visitorMapper.mapRow(rs);
                visitors.add(animal);
            }
        }catch (SQLException e){
            log.error(e.getMessage(),e);
        }
        return visitors;
    }

    public int addVisitorWithVisits(Visitor visitor) {
        String sql = SQLQueries.INSERT_VISITORS;
        try (Connection conn = dbConnectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            conn.setAutoCommit(false);
            ps.setString(1, visitor.getName());
            ps.setString(2, visitor.getEmail());
            ps.setInt(3,visitor.getTickets());
            int rows = ps.executeUpdate();
            if (rows== 0) {
                conn.rollback();
            }
            conn.commit();
            return 1;

        }catch (SQLException e) {
            log.error(e.getMessage(),e);
        }
        return 0;
    }
}
