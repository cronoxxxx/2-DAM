package org.example.dao.hibernate.repo;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.example.dao.hibernate.jpautil.JPAUtil;
import org.example.dao.hibernate.model.Spy;

import java.util.List;

public class SpyRepoJPA {
    private final JPAUtil jpaUtil;
    private EntityManager em;
    @Inject
    public SpyRepoJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public List<Spy> findAll() {
        em = jpaUtil.getEntityManager();
       return em.createQuery("SELECT p from Spy p", Spy.class).getResultList();

    }
}
