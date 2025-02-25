package org.example.dao.hibernate.repo;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.example.dao.hibernate.jpautil.JPAUtil;
import org.example.dao.hibernate.model.WeaponsFaction;

public class FactionWeaponRepoJPA {
    private final JPAUtil jpaUtil;
    private EntityManager em;
    @Inject
    public FactionWeaponRepoJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public int addCrossRef(WeaponsFaction weaponsFaction){
        int result = 0;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(weaponsFaction);
            tx.commit();
            result = 1;
        } catch (NoResultException e) {
            if (tx.isActive()) tx.rollback();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }
}
