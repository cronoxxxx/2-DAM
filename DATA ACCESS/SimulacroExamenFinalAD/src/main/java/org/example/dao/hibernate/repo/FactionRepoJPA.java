package org.example.dao.hibernate.repo;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.example.dao.hibernate.jpautil.JPAUtil;
import org.example.dao.hibernate.model.Faction;
import org.example.dao.hibernate.model.Weapon;

import java.util.List;

public class FactionRepoJPA {
    private final JPAUtil jpaUtil;
    private EntityManager em;


    @Inject
    public FactionRepoJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public List<Faction> getAll() {
        em = jpaUtil.getEntityManager();
        return em.createQuery("SELECT p FROM Faction p", Faction.class).getResultList();
    }

    public void save(Faction faction) {

        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(faction);
            tx.commit();

        } catch (NoResultException e) {
            if (tx.isActive()) tx.rollback();
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Weapon> getWeaponsByFaction(String factionName) {
        em = jpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT wf.idWeapon FROM WeaponsFaction wf WHERE wf.nameFaction.fname = :factionName",
                            Weapon.class)
                    .setParameter("factionName", factionName)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
