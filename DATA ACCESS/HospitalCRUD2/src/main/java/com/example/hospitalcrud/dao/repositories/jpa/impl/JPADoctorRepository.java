package com.example.hospitalcrud.dao.repositories.jpa.impl;

import com.example.hospitalcrud.dao.model.Doctor;
import com.example.hospitalcrud.dao.repositories.DoctorRepository;
import com.example.hospitalcrud.dao.repositories.jpa.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
@Log4j2
@Repository
public class JPADoctorRepository implements DoctorRepository {
    private final JPAUtil jpaUtil;
    private EntityManager em;

    public JPADoctorRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = null;
        em = jpaUtil.getEntityManager();
        try {
            doctors = em.createNamedQuery("Doctor.getAll", Doctor.class).getResultList();
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (em != null) em.close();
        }
        return doctors;
    }
}
