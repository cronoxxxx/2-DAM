package org.example.loginspring_adriansaavedra.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

    public List<PlayerEntity> getPlayersForUser(String username) {
        CredentialEntity credential = getCredentialByUsername(username);
        return new ArrayList<>(credential.getFavoritePlayerEntities());
    }

    public PlayerEntity getPlayerForUser(String username, int playerId) {
        CredentialEntity credential = getCredentialByUsername(username);
        return credential.getFavoritePlayerEntities().stream()
                .filter(p -> p.getId() == playerId)
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException(Constantes.PLAYER_NOT_FOUND));
    }

    public PlayerEntity addPlayerForUser(String username, String playerName) {
        CredentialEntity credential = getCredentialByUsername(username);
        if (!credential.isVerified()) {
            throw new UserNotFoundException(Constantes.USER_NOT_VERIFIED);
        }

        PlayerEntity player;
        try {
            player = entityManager.createQuery(Constantes.SQL_ADD_PLAYER_FOR_USER, PlayerEntity.class)
                    .setParameter(Constantes.NAME_PARAM, playerName)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new PlayerNotFoundException(Constantes.PLAYER_NOT_FOUND);
        }

        if (credential.getFavoritePlayerEntities().contains(player)) {
            throw new PlayerAlreadyExistsException(Constantes.PLAYER_FAVORITE_ALREADY_EXISTS);
        }
        credential.getFavoritePlayerEntities().add(player);
        entityManager.merge(credential);
        return player;
    }



    public void deletePlayerForUser(String username, int playerId) {
        CredentialEntity credential = getCredentialByUsername(username);
        PlayerEntity player = entityManager.find(PlayerEntity.class, playerId);

        if (player == null) {
            throw new PlayerNotFoundException(Constantes.PLAYER_NOT_FOUND);
        }

        if (!credential.getFavoritePlayerEntities().remove(player)) {
            throw new PlayerNotFoundException(Constantes.PLAYER_NOT_IN_FAVORITES);
        }

        entityManager.merge(credential);
    }



    private CredentialEntity getCredentialByUsername(String username) {
        try {
            return entityManager.createQuery(Constantes.SQL_GET_CREDENTIAL_BY_USERNAME, CredentialEntity.class)
                    .setParameter(Constantes.USERNAME_PARAM, username)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new UserNotFoundException(Constantes.USER_NOT_FOUND);
        }
    }
}