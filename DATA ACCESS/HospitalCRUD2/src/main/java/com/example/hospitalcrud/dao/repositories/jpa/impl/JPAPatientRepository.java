package com.example.hospitalcrud.dao.repositories.jpa.impl;

import com.example.hospitalcrud.dao.model.Patient;
import com.example.hospitalcrud.dao.repositories.PatientRepository;
import com.example.hospitalcrud.dao.repositories.jpa.utils.JPAUtil;
import jakarta.persistence.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class JPAPatientRepository implements PatientRepository {
    private final JPAUtil jpaUtil;
    private EntityManager em;

    public JPAPatientRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> patients = null;
        em = jpaUtil.getEntityManager();
        try {
            patients = em.createNamedQuery("Patient.findAll", Patient.class).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }
        return patients;
    }

    @Override
    public int add(Patient patient) {
        int result = 0;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(patient); // This will also persist the Credential if it's set in Patient
            tx.commit();
            result = 1;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }

        return result;
    }


    @Override
    public void delete(int id, boolean confirm) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Patient patient = em.find(Patient.class, id);

            if (patient != null) {
                em.remove(patient); // This will also remove the associated Credential due to cascade
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            log.warn(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }
    }



    @Override
    public void update(Patient patient) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(patient);
            tx.commit();
        } catch (NoResultException e) {
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
