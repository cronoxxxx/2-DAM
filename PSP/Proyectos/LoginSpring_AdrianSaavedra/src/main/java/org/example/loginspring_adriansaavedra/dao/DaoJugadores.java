package org.example.loginspring_adriansaavedra.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.common.errors.PlayerAlreadyExistsException;
import org.example.loginspring_adriansaavedra.common.errors.PlayerNotFoundException;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DaoJugadores {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PlayerEntity> getAllPlayers() {
        return entityManager.createQuery("SELECT p FROM PlayerEntity p", PlayerEntity.class).getResultList();
    }

    public void addPlayer(PlayerEntity playerEntity) {
        if (playerExists(playerEntity.getName())) {
            throw new PlayerAlreadyExistsException("El jugador " + playerEntity.getName() + " ya existe");
        }
        entityManager.persist(playerEntity);
    }

    public void updatePlayer(PlayerEntity updatedPlayerEntity) {
        PlayerEntity existingPlayer = entityManager.find(PlayerEntity.class, updatedPlayerEntity.getId());
        if (existingPlayer == null) {
            throw new PlayerNotFoundException("Jugador no encontrado con ID: " + updatedPlayerEntity.getId());
        }
        entityManager.merge(updatedPlayerEntity);
    }

    public void deletePlayer(int id) {
        PlayerEntity player = entityManager.find(PlayerEntity.class, id);
        if (player == null) {
            throw new PlayerNotFoundException("Jugador no encontrado con ID: " + id);
        }
        entityManager.remove(player);
    }

    public PlayerEntity getPlayerById(int id) {
        PlayerEntity player = entityManager.find(PlayerEntity.class, id);
        if (player == null) {
            throw new PlayerNotFoundException(Constantes.PLAYER_NOT_FOUND);
        }
        return player;
    }

    private boolean playerExists(String name) {
        Long count = entityManager.createQuery("SELECT COUNT(p) FROM PlayerEntity p WHERE LOWER(p.name) = LOWER(:name)", Long.class)
                .setParameter("name", name)
                .getSingleResult();
        return count > 0;
    }
}
