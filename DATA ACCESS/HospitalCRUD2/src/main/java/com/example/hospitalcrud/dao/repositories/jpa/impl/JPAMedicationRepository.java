package com.example.hospitalcrud.dao.repositories.jpa.impl;


import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Medication;
import com.example.hospitalcrud.dao.repositories.MedicationRepository;
import com.example.hospitalcrud.dao.repositories.jpa.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class JPAMedicationRepository implements MedicationRepository {
    private final JPAUtil jpaUtil;

    public JPAMedicationRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Medication> findByRecordId(int recordId) {
        EntityManager em = jpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT m FROM Medication m WHERE m.medRecord.id = :recordId", Medication.class)
                    .setParameter("recordId", recordId)
                    .getResultList();
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void addMedication(int recordId, Medication medication) {
        EntityManager em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            MedRecord medRecord = em.find(MedRecord.class, recordId);
            if (medRecord != null) {
                medication.setMedRecord(medRecord);
                em.persist(medication);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error("Error adding medication: ", e);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void deleteMedicationsByRecordId(int recordId) {
        EntityManager em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM Medication m WHERE m.medRecord.id = :recordId")
                    .setParameter("recordId", recordId)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error("Error deleting medications by record ID: ", e);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void deleteMedicationsByPatientId(int patientId) {
        EntityManager em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery(
                            "DELETE FROM Medication m WHERE m.medRecord.patient.id = :patientId")
                    .setParameter("patientId", patientId)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error("Error deleting medications by patient ID: ", e);
        } finally {
            if (em != null) em.close();
        }
    }
}