package org.example.dao.hibernate.repo;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.example.dao.hibernate.jpautil.JPAUtil;
import org.example.dao.hibernate.model.Habitat;
import org.example.dao.hibernate.model.Visitor;

public class VisitorDAO {
    private final JPAUtil jpaUtil;
    private EntityManager em;
    @Inject
    public VisitorDAO(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public Visitor findByName(String name) {
        em = jpaUtil.getEntityManager();

        return em.createQuery("SELECT v FROM Visitor v WHERE v.name = :name", Visitor.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Habitat findByHabitat(String s) {
        em = jpaUtil.getEntityManager();

        return em.createQuery("SELECT v FROM Habitat v WHERE v.name = :name", Habitat.class)
                .setParameter("name", s).getSingleResult();
    }


}