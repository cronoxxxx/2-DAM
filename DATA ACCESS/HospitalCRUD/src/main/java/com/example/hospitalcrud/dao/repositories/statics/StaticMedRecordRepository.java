package com.example.hospitalcrud.dao.repositories.statics;

import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Medication;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Repository
@Profile("static")
public class StaticMedRecordRepository implements MedRecordRepository {
    private static final List<MedRecord> medRecords = new ArrayList<>();

    public StaticMedRecordRepository() {
            medRecords.add(new MedRecord(1, 1, 1, "Hipertensión arterial", LocalDate.of(2022, 1, 10), Arrays.asList(
                    new Medication(1, "Amlodipino", 1),
                    new Medication(2, "Hidroclorotiazida", 1)
            )));

            medRecords.add(new MedRecord(2, 1, 2, "Diabetes tipo 2", LocalDate.of(2022, 2, 20), Arrays.asList(
                    new Medication(3, "Metformina", 2),
                    new Medication(4, "Glipizida", 2)
            )));

            medRecords.add(new MedRecord(3, 1, 1, "Asma bronquial", LocalDate.of(2022, 3, 15), Arrays.asList(
                    new Medication(5, "Salbutamol", 3),
                    new Medication(6, "Fluticasona", 3)
            )));

            medRecords.add(new MedRecord(4, 2, 1, "Infección respiratoria", LocalDate.of(2022, 4, 10), Arrays.asList(
                    new Medication(7, "Amoxicilina", 4),
                    new Medication(8, "Clavulanato", 4)
            )));

            medRecords.add(new MedRecord(5, 3, 3, "Lesión deportiva", LocalDate.of(2022, 5, 25), Arrays.asList(
                    new Medication(9, "Ibuprofeno", 5),
                    new Medication(10, "Paracetamol", 5)
            )));

            medRecords.add(new MedRecord(6, 4, 2, "Enfermedad cardiovascular", LocalDate.of(2022, 6, 15), Arrays.asList(
                    new Medication(11, "Atorvastatina", 6),
                    new Medication(12, "Aspirina", 6)
            )));

            medRecords.add(new MedRecord(7, 5, 3, "Cáncer de mama", LocalDate.of(2022, 7, 10), Arrays.asList(
                    new Medication(13, "Tamoxifeno", 7),
                    new Medication(14, "Anastrozol", 7)
            )));

            medRecords.add(new MedRecord(8, 4, 1, "Enfermedad renal crónica", LocalDate.of(2022, 8, 25), Arrays.asList(
                    new Medication(15, "Epoetina alfa", 8),
                    new Medication(16, "Ferrosa", 8)
            )));

            medRecords.add(new MedRecord(9, 5, 3, "Enfermedad pulmonar obstructiva crónica", LocalDate.of(2022, 9, 15), Arrays.asList(
                    new Medication(17, "Salbutamol", 9),
                    new Medication(18, "Tiotropio", 9)
            )));

            medRecords.add(new MedRecord(10, 5, 2, "Enfermedad de Alzheimer", LocalDate.of(2022, 10, 10), Arrays.asList(
                    new Medication(19, "Donepezilo", 10),
                    new Medication(20, "Rivastigmina", 10)
            )));
    }

    public List<MedRecord> findAll() {
        return new ArrayList<>(medRecords);
    }

    @Override
    public int add(MedRecord medRecord) {
        return 0;
    }

    @Override
    public void update(MedRecord medRecord) {

    }

    @Override
    public void delete(MedRecord medRecord) {

    }

    @Override
    public void delete(int patientId) {

    }


}