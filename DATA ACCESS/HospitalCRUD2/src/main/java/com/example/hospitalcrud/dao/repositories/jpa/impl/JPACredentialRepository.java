package com.example.hospitalcrud.dao.repositories.jpa.impl;

import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.repositories.CredentialRepository;
import com.example.hospitalcrud.dao.repositories.jpa.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class JPACredentialRepository implements CredentialRepository {
    private final JPAUtil jpaUtil;
    private EntityManager em;

    public JPACredentialRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public Credential get(String username) {
        Credential credential = null;
        em = jpaUtil.getEntityManager();
        try {
            credential = em.createQuery("SELECT c FROM Credential c WHERE c.userName = :username", Credential.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }
        return credential;
    }

    @Override
    public void add(Credential credential) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(credential);
            tx.commit();
        } catch (NoResultException e) {
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public Credential getByPatientId(int patientId) {
        Credential credential = null;
        em = jpaUtil.getEntityManager();
        try {
            credential = em.createQuery("SELECT c FROM Credential c WHERE c.patient.id = :patientId", Credential.class)
                    .setParameter("patientId", patientId)
                    .getSingleResult();


        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }
        return credential;
    }

    @Override
    public void delete(int id) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Credential credential = em.find(Credential.class, id);
            if (credential != null) {
                em.remove(credential);
            }
            tx.commit();
        } catch (NoResultException e) {
            if (tx.isActive()) tx.rollback();
            log.error(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }
    }
}
