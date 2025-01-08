package org.example.dao.jdbc;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.SQLQueries;
import org.example.common.config.Configuration;
import org.example.dao.jdbc.utils.DBConnectionPool;
import org.example.dao.mappers.jdbc.FactionMapper;
import org.example.dao.xml.FactionXML;
import org.example.dao.xml.Factions;
import org.example.dao.xml.WeaponXML;
import org.example.domain.model.Faction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoFactions {
    private final Path factionsFilePath;
    private final DBConnectionPool dbConnectionPool;
    private final FactionMapper factionMapper;

    @Inject
    public DaoFactions(DBConnectionPool dbConnectionPool, FactionMapper factionMapper) {
        this.dbConnectionPool = dbConnectionPool;
        this.factionMapper = factionMapper;
        Configuration config = Configuration.getInstance();
        factionsFilePath = Paths.get(config.getProperty("pathFactions"));
        try {
            if (!Files.exists(factionsFilePath)) {
                Files.createDirectories(factionsFilePath.getParent());
                initializeXMLFile();
            } else if (Files.size(factionsFilePath) == 0) {
                initializeXMLFile();
            }
        } catch (IOException e) {
            log.error("Error initializing XML file: {}", e.getMessage(), e);
        }
        }


    private void initializeXMLFile() { //xml
        Factions medRecordsXML = new Factions(new ArrayList<>());
        try {
            JAXBContext context = JAXBContext.newInstance(Factions.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(medRecordsXML, Files.newOutputStream(factionsFilePath));
        } catch (JAXBException | IOException e) {
            log.error("Error initializing XML file: {}", e.getMessage(), e);
        }
    }

    public List<Faction> getAll (){
        List<Faction> factions = new ArrayList<>();
        try (Connection connection = dbConnectionPool.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQLQueries.GET_ALL_FACTIONS);
            while (rs.next()) {
                Faction faction = factionMapper.mapRow(rs);
                factions.add(faction);
            }
        }catch (SQLException e){
            log.error("Error getting all factions: {}", e.getMessage(), e);
        }
        return factions;

    }




    public Factions findAllXML() { //xml
        try {
            JAXBContext context = JAXBContext.newInstance(Factions.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Factions) unmarshaller.unmarshal(Files.newInputStream(factionsFilePath));
        } catch (JAXBException | IOException e) {
            log.error("Error reading XML file: {}", e.getMessage(), e);
            return new Factions(new ArrayList<>());
        }
    }

    public int saveFactionsXML(Factions factions) {
        try (Connection con = dbConnectionPool.getConnection()) {
            con.setAutoCommit(false);
            for (FactionXML f : factions.getFactions()) {
                try (PreparedStatement ps = con.prepareStatement(SQLQueries.INSERT_FACTION,Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, f.getName());
                    ps.setString(2, f.getContact());
                    ps.setString(3, f.getPlanet());
                    ps.setInt(4, f.getNumberCS());
                    ps.setDate(5, Date.valueOf(f.getDateLastPurchase()));
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 0) {
                        con.rollback();
                    }
                    saveWeapons(con, f);
                }
            }
            con.commit();
            return 1;
        } catch (SQLException e) {
           log.error(e.getMessage(), e);
        }
        return 0;
    }
    private void saveWeapons(Connection con, FactionXML faction) { //XML
        if (faction.getWeapons() != null) {
            for (WeaponXML w : faction.getWeapons()) {
                try (PreparedStatement ps = con.prepareStatement(SQLQueries.INSERT_WEAPON, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, w.getName());
                    ps.setInt(2, w.getPrice());
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 0) {
                        con.rollback();
                    }
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        int weaponId = rs.getInt(1);
                        saveWeaponFaction(con, faction.getName(), weaponId);
                    }

                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

    }

    private void saveWeaponFaction(Connection conn, String factionName, int weaponId) { //XML
        try (PreparedStatement ps = conn.prepareStatement(SQLQueries.INSERT_FACTION_WEAPON, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, factionName);
            ps.setInt(2, weaponId);
            ps.executeUpdate();
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                conn.rollback();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }


    }


}
