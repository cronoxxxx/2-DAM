package com.example.hospitalcrud.dao.repositories.jdbc;


import com.example.hospitalcrud.dao.mappers.jdbc.MedRecordRowMapper;
import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Medication;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import com.example.hospitalcrud.dao.repositories.jdbc.utils.DBConnectionPool;
import com.example.hospitalcrud.dao.repositories.jdbc.utils.SQLQueries;
import com.example.hospitalcrud.domain.errors.ForeignKeyConstraintError;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
@Log4j2
@Repository
@Profile("files")
public class JDBCMedRecordRepository implements MedRecordRepository {
    private final DBConnectionPool dbConnectionPool;
    private final MedRecordRowMapper medRecordRowMapper;
    public JDBCMedRecordRepository(DBConnectionPool dbConnectionPool, MedRecordRowMapper medRecordRowMapper) {
        this.dbConnectionPool =  dbConnectionPool;
        this.medRecordRowMapper = medRecordRowMapper;
    }

    @Override
    public List<MedRecord> findAll() {
        Map<Integer, MedRecord> medRecordMap = new HashMap<>();
        String sql = SQLQueries.GET_ALL_MEDICAL_RECORDS_WITH_MEDICATIONS;

        try (Connection connection = dbConnectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                MedRecord medRecord = medRecordRowMapper.mapRow(resultSet);
                int recordId = medRecord.getId();
                if (medRecordMap.containsKey(recordId)) {
                    MedRecord existingRecord = medRecordMap.get(recordId);
                    if (!medRecord.getMedications().isEmpty()) {
                        existingRecord.setMedications(medRecord.getMedications());
                    }
                } else {
                    medRecordMap.put(recordId, medRecord);
                }
            }
        } catch (SQLException e) {
            throw new ForeignKeyConstraintError("Error fetching all medical records: " + e.getMessage());
        }

        return new ArrayList<>(medRecordMap.values());
    }

    @Override
    public int add(MedRecord medRecord) {
        int medRecordId;
        String sqlMedRecord = SQLQueries.ADD_MEDICAL_RECORD_WITH_MEDICATIONS;
        String sqlMedications = SQLQueries.ADD_PRESCRIBED_MEDICATIONS;
        Connection conn = null;
        try {
            conn = dbConnectionPool.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement pstmtMedRecord = conn.prepareStatement(sqlMedRecord, Statement.RETURN_GENERATED_KEYS)) {
                pstmtMedRecord.setInt(1, medRecord.getIdPatient());
                pstmtMedRecord.setInt(2, medRecord.getIdDoctor());
                pstmtMedRecord.setString(3, medRecord.getDiagnosis());
                LocalDate date = medRecord.getDate();
                if (date == null) {
                    throw new ForeignKeyConstraintError("Date cannot be null");
                }
                pstmtMedRecord.setDate(4, Date.valueOf(date));
                pstmtMedRecord.executeUpdate();
                try (ResultSet rs = pstmtMedRecord.getGeneratedKeys()) {
                    if (rs.next()) {
                        medRecordId = rs.getInt(1);
                    } else {
                        throw new ForeignKeyConstraintError("Creating med record failed, no ID obtained.");
                    }
                }
            }
            try (PreparedStatement pstmtMedications = conn.prepareStatement(sqlMedications, Statement.RETURN_GENERATED_KEYS)) {
                pstmtMedications.setInt(1, medRecordId);
                for (Medication medication : medRecord.getMedications()) {
                    pstmtMedications.setString(2, medication.getMedicationName());
                    pstmtMedications.addBatch();
                }
                pstmtMedications.executeBatch();

                try (ResultSet rs = pstmtMedications.getGeneratedKeys()) {
                    for (int i = 0; rs.next(); i++) {
                        int medicationId = rs.getInt(1);
                        medRecord.getMedications().get(i).setId(medicationId);
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new ForeignKeyConstraintError("Error rolling back transaction: " + ex.getMessage());
            }
            throw new ForeignKeyConstraintError("Error adding medical record: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    log.error("Error adding medical record: {}", e.getMessage());
                }
            }
        }

        return medRecordId;
    }


    @Override
    public void update(MedRecord medRecord) {
        Connection conn = null;
        try {
            conn = dbConnectionPool.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(SQLQueries.UPDATE_MED_RECORD)) {
                pstmt.setString(1, medRecord.getDiagnosis());
                pstmt.setDate(2, Date.valueOf(medRecord.getDate()));
                pstmt.setInt(3, medRecord.getIdDoctor());
                pstmt.setInt(4, medRecord.getId());
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(SQLQueries.DELETE_MEDICATIONS)) {
                pstmt.setInt(1, medRecord.getId());
                pstmt.executeUpdate();
            }

            if (medRecord.getMedications() != null && !medRecord.getMedications().isEmpty()) {
                try (PreparedStatement pstmt = conn.prepareStatement(SQLQueries.INSERT_MEDICATION)) {
                    for (Medication medication : medRecord.getMedications()) {
                        pstmt.setInt(1, medRecord.getId());
                        pstmt.setString(2, medication.getMedicationName());
                        pstmt.addBatch();
                    }
                    pstmt.executeBatch();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new ForeignKeyConstraintError("Error rolling back transaction: " + ex.getMessage());
            }
            throw new ForeignKeyConstraintError("Error updating medical record: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    log.error("Error updating medical record: {}", e.getMessage());
                }
            }
        }
    }

    @Override
    public void delete(MedRecord medRecord) {
        Connection conn = null;
        try{
            conn = dbConnectionPool.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(SQLQueries.DELETE_MEDICATIONS)) {
                pstmt.setInt(1, medRecord.getId());
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = conn.prepareStatement(SQLQueries.DELETE_MED_RECORD)) {
                pstmt.setInt(1, medRecord.getId());
                pstmt.executeUpdate();
            }
            conn.commit();
        }catch (SQLException e) {
            throw new ForeignKeyConstraintError("Error deleting medical record: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    log.error("Error deleting medical record: {}", e.getMessage());
                }
            }
        }



    }

    @Override
    public void delete(int patientId) {
        Connection connection = null;
        try {
            connection = dbConnectionPool.getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.DELETE_MEDICATIONS_BY_PATIENT_ID)) {
                statement.setInt(1, patientId);
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.DELETE_MED_RECORDS_BY_PATIENT_ID)) {
                statement.setInt(1, patientId);
                statement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new ForeignKeyConstraintError("Cannot delete patient and his medRecords");
            }
            throw new IllegalArgumentException("Error deleting medical records for patient", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    log.error("Error deleting medical records for patient: {}", e.getMessage());
                }
            }
        }
    }
}
