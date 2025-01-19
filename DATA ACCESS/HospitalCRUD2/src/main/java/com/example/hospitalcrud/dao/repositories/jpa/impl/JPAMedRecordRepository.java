package com.example.hospitalcrud.dao.repositories.jpa.impl;

import com.example.hospitalcrud.dao.model.MedRecord;
import com.example.hospitalcrud.dao.model.Medication;
import com.example.hospitalcrud.dao.repositories.MedRecordRepository;
import com.example.hospitalcrud.dao.repositories.jpa.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Log4j2
public class JPAMedRecordRepository implements MedRecordRepository {
    private final JPAUtil jpaUtil;

    public JPAMedRecordRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<MedRecord> findAll() {
        try (EntityManager em = jpaUtil.getEntityManager()) {
            return em.createQuery("SELECT DISTINCT m FROM MedRecord m LEFT JOIN FETCH m.medications", MedRecord.class).getResultList();
        }
    }

    @Override
    public int add(MedRecord medRecord) {
        EntityManager em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Medication medication : medRecord.getMedications()) {
                medication.setMedRecord(medRecord);
            }
            em.persist(medRecord);
            tx.commit();
            return medRecord.getId();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
            return 0;
        } finally {
            if (em != null) em.close();
        }
    }


    @Override
    public void update(MedRecord medRecord) {
        EntityManager em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.createQuery("DELETE FROM Medication m WHERE m.medRecord.id = :medRecordId")
                    .setParameter("medRecordId", medRecord.getId())
                    .executeUpdate();

            for (Medication medication : medRecord.getMedications()) {
                em.persist(medication);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }
    }


    @Override
    public List<MedRecord> findByPatientId(int patientId) {
        EntityManager em = jpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT m FROM MedRecord m LEFT JOIN FETCH m.medications WHERE m.patient.id = :patientId", MedRecord.class)
                    .setParameter("patientId", patientId)
                    .getResultList();
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public void delete(MedRecord medRecord) {
        EntityManager em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.find(MedRecord.class, medRecord));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int patientId) {
        EntityManager em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM MedRecord m WHERE m.patient.id = :patientId")
                    .setParameter("patientId", patientId)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
