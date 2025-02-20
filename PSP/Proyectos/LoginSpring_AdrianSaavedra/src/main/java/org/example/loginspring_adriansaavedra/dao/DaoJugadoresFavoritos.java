package org.example.loginspring_adriansaavedra.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.common.errors.PlayerAlreadyExistsException;
import org.example.loginspring_adriansaavedra.common.errors.PlayerNotFoundException;
import org.example.loginspring_adriansaavedra.common.errors.UserNotFoundException;
import org.example.loginspring_adriansaavedra.domain.model.CredentialEntity;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoJugadoresFavoritos {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PlayerEntity> getPlayersForCredential(int credentialId) {
        CredentialEntity credential = entityManager.find(CredentialEntity.class, credentialId);
        if (credential == null) {
            throw new UserNotFoundException(Constantes.USER_NOT_FOUND);
        }
        return new ArrayList<>(credential.getFavoritePlayerEntities());
    }

    public PlayerEntity getPlayerForCredential(int credentialId, int playerId) {
        CredentialEntity credential = entityManager.find(CredentialEntity.class, credentialId);
        if (credential == null) {
            throw new UserNotFoundException(Constantes.USER_NOT_FOUND);
        }
        return credential.getFavoritePlayerEntities().stream()
                .filter(p -> p.getId() == playerId)
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException(Constantes.PLAYER_NOT_FOUND));
    }

    public PlayerEntity addPlayerForCredential(int credentialId, String playerName) {
        CredentialEntity credential = entityManager.find(CredentialEntity.class, credentialId);
        if (credential == null) {
            throw new UserNotFoundException(Constantes.USER_NOT_FOUND);
        }
        if (!credential.isVerified()) {
            throw new UserNotFoundException(Constantes.USER_NOT_VERIFIED);
        }
        PlayerEntity player = entityManager.createQuery("SELECT p FROM PlayerEntity p WHERE LOWER(p.name) = LOWER(:name)", PlayerEntity.class)
                .setParameter("name", playerName)
                .getSingleResult();
        if (player == null) {
            throw new PlayerNotFoundException(Constantes.PLAYER_NOT_FOUND);
        }
        if (credential.getFavoritePlayerEntities().contains(player)) {
            throw new PlayerAlreadyExistsException(Constantes.PLAYER_FAVORITE_ALREADY_EXISTS);
        }
        credential.getFavoritePlayerEntities().add(player);
        entityManager.merge(credential);
        return player;
    }

    public void deletePlayerForCredential(int credentialId, int playerId) {
        CredentialEntity credential = entityManager.find(CredentialEntity.class, credentialId);
        if (credential == null) {
            throw new UserNotFoundException(Constantes.USER_NOT_FOUND);
        }
        PlayerEntity player = entityManager.find(PlayerEntity.class, playerId);
        if (player == null) {
            throw new PlayerNotFoundException(Constantes.PLAYER_NOT_FOUND);
        }
        credential.getFavoritePlayerEntities().remove(player);
        entityManager.merge(credential);
    }
}