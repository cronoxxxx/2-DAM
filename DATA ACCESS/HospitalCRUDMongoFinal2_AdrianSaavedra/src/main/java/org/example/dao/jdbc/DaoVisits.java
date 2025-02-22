package org.example.dao.jdbc;

import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.example.common.SQLQueries;
import org.example.common.config.Configuration;
import org.example.dao.jdbc.utils.DBConnectionPool;
import org.example.dao.xml.AnimalVisits;
import org.example.domain.model.Visit;

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
public class DaoVisits {
    private final Path visitsFilePath;

    private final DBConnectionPool dbConnectionPool;
    @Inject
    public DaoVisits(DBConnectionPool dbConnectionPool) {
        this.dbConnectionPool = dbConnectionPool;
        Configuration config = Configuration.getInstance();
        visitsFilePath = Paths.get(config.getProperty("pathVisits"));
        try {
            if (!Files.exists(visitsFilePath)) {
                Files.createDirectories(visitsFilePath.getParent());
                initializeXMLFile();
            } else if (Files.size(visitsFilePath) == 0) {
                initializeXMLFile();
            }
        } catch (IOException e) {
            log.error("Error initializing XML file: {}", e.getMessage(), e);
        }
    }

    private void initializeXMLFile() { //xml
        AnimalVisits animalVisits = new AnimalVisits(new ArrayList<>());
        try {
            JAXBContext context = JAXBContext.newInstance(AnimalVisits.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(animalVisits, Files.newOutputStream(visitsFilePath));
        } catch (JAXBException | IOException e) {
            log.error("Error initializing XML file: {}", e.getMessage(), e);
        }
    }

    public AnimalVisits findAllXML (){
        try {
            JAXBContext context = JAXBContext.newInstance(AnimalVisits.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (AnimalVisits) unmarshaller.unmarshal(Files.newInputStream(visitsFilePath));
        } catch (JAXBException | IOException e) {
            log.error("Error reading XML file: {}", e.getMessage(), e);
            return new AnimalVisits(new ArrayList<>());
        }
    }

    //save database
    public int saveVisits(List<Visit> visitList) {
        int rowsAffected = 0;
        try (Connection conn = dbConnectionPool.getConnection()) {
            conn.setAutoCommit(false);
            for (Visit visit : visitList) {
                PreparedStatement ps = conn.prepareStatement(SQLQueries.INSERT_VISIT, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, visit.getAnimalID());
                ps.setInt(2, visit.getVisitorID());
                ps.setDate(3, Date.valueOf(visit.getVisitDate()));
                rowsAffected += ps.executeUpdate();
            }
            if (rowsAffected == 1) {
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            log.error("Error saving visits: {}", e.getMessage(), e);
        }
        return rowsAffected;
    }







}
