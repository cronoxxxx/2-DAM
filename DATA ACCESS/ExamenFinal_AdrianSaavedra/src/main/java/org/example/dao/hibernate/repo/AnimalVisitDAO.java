package org.example.dao.hibernate.repo;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.bson.Document;
import org.example.dao.hibernate.jpautil.JPAUtil;
import org.example.dao.hibernate.model.AnimalVisit;

import java.util.List;

public class AnimalVisitDAO {
    private final JPAUtil jpaUtil;
    private EntityManager em;
    @Inject
    public AnimalVisitDAO(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;

    }

    public void save(AnimalVisit animalVisit) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(animalVisit);
            tx.commit();
        }catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<AnimalVisit> findByAnimalId(Integer animalId) {
        em = jpaUtil.getEntityManager();

        return em.createQuery("SELECT av FROM AnimalVisit av WHERE av.animal.animalId = :animalId", AnimalVisit.class)
                .setParameter("animalId", animalId)
                .getResultList();
    }
}