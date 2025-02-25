package org.example.dao.hibernate.repo;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import lombok.extern.log4j.Log4j2;
import org.example.dao.hibernate.jpautil.JPAUtil;
import org.example.dao.hibernate.model.Faction;
import org.example.dao.hibernate.model.Weapon;

import java.util.List;

public class WeaponRepoJPA {
    private final JPAUtil jpaUtil;
    private EntityManager em;
    @Inject
    public WeaponRepoJPA(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }

    public List<Weapon> getAll() {
        em = jpaUtil.getEntityManager();
        return em.createQuery("SELECT p FROM Weapon p", Weapon.class).getResultList();
    }

    public int addWeapon(Weapon weapon) {
        int result = 0;
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(weapon);
            tx.commit();
            result = 1;
        } catch (NoResultException e) {
            if (tx.isActive()) tx.rollback();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    public void updateWeapon(Weapon weapon) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Weapon existingWeapon = em.find(Weapon.class, weapon.getId());
            if (existingWeapon != null) {
                existingWeapon.setWprice(weapon.getWprice());
                em.merge(existingWeapon);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Weapon findByName(String name) {
        em = jpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT w FROM Weapon w WHERE w.wname = :name", Weapon.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void deleteWeapon(Weapon weapon) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Weapon attachedWeapon = em.merge(weapon);
            em.remove(attachedWeapon);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
