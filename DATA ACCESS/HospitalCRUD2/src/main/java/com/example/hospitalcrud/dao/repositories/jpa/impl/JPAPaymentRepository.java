package com.example.hospitalcrud.dao.repositories.jpa.impl;

import com.example.hospitalcrud.dao.repositories.PaymentRepository;
import com.example.hospitalcrud.dao.repositories.jpa.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class JPAPaymentRepository implements PaymentRepository {
    private final JPAUtil jpaUtil;

    public JPAPaymentRepository(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    @Override
    public double getAllPayments(int patientId) {
        try (EntityManager em = jpaUtil.getEntityManager()) {
            Double result = em.createNamedQuery("Payment.getPaymentsByPatient", Double.class)
                    .setParameter("patientId", patientId)
                    .getSingleResult();
            return result != null ? result : 0.0;
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            return 0.0;
        }
    }
}
