package org.example.dao.hibernate.repo;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.dao.hibernate.jpautil.JPAUtil;
import org.example.dao.hibernate.model.Animal;
import org.example.dao.hibernate.model.Habitat;

import java.util.List;


public class AnimalDAO {
    private final JPAUtil jpaUtil;
    private EntityManager em;

    @Inject
    public AnimalDAO(JPAUtil jpaUtil) {
        this.jpaUtil = jpaUtil;
    }


    public List<Animal> getAll() {
        em = jpaUtil.getEntityManager();

        return em.createQuery("SELECT a FROM Animal a", Animal.class).getResultList();
    }

    public List<Animal> getAllSavanah(Habitat habitat) {
        int id = habitat.getId();
        em = jpaUtil.getEntityManager();
        return em.createQuery("SELECT a FROM Animal a where animalId=:id", Animal.class).setParameter("id", id).getResultList();
    }

    public void delete(Animal animal) {
        em = jpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            animal = em.merge(animal);
            em.remove(animal);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Animal findByName(String name) {
        em = jpaUtil.getEntityManager();
        return em.createQuery("SELECT v FROM Animal v WHERE v.name = :name", Animal.class)
                .setParameter("name", name).getSingleResult();
    }
}
