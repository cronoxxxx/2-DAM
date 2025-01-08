package com.example.hospitalcrud.dao.mappers.jdbc;
import com.example.hospitalcrud.dao.model.Patient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
@Profile("files")
public class PatientRowMapper {
    public Patient mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("patient_id");
        String name = rs.getString("name");
        String phone = rs.getString("phone");
        LocalDate birthday = rs.getDate("date_of_birth").toLocalDate();
        return new Patient(id, name, phone, birthday);
    }
}
