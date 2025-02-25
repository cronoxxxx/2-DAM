package org.example.dao.hibernate.repo;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.example.dao.hibernate.jpautil.JPAUtil;
import org.example.dao.hibernate.model.Battle;

import java.util.List;

public class BattleRepoJPA {
    private final JPAUtil jpaUtil;
    private EntityManager em;
    @Inject
    public BattleRepoJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public List<Battle> getAllBattles() {
        em = jpaUtil.getEntityManager();
        return em.createQuery("SELECT p from Battle p", Battle.class).getResultList();
    }

    public int saveBattle(Battle battle) {
        int result = 0;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(battle);
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
