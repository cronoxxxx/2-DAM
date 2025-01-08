package com.example.hospitalcrud.dao.repositories.jpa.utils;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Component;

@Component
public class JPAUtil {
    private final EntityManagerFactory emf;

    public JPAUtil() {
        emf = Persistence.createEntityManagerFactory("hospital.hibernate");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
