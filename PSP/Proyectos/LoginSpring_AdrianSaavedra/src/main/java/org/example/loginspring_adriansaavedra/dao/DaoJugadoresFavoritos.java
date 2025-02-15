package org.example.loginspring_adriansaavedra.dao;

import org.example.loginspring_adriansaavedra.common.errors.PlayerNotFoundException;
import org.example.loginspring_adriansaavedra.common.errors.UserNotFoundException;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DaoJugadoresFavoritos {
    private final Database database;

    public DaoJugadoresFavoritos(Database database) {
        this.database = database;
    }

    public List<Player> getPlayersForCredential(int credentialId) {
        return database.getCredentials().stream()
                .filter(c -> c.getId() == credentialId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("El usuario no existe"))
                .getJugadoresFavoritos();
    }

    public Player getPlayerForCredential(int credentialId, int playerId) {
        return database.getCredentials().stream()
                .filter(c -> c.getId() == credentialId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("El usuario no existe"))
                .getJugadoresFavoritos().stream()
                .filter(p -> p.getId() == playerId)
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("El jugador no existe"));
    }

    public void addPlayerForCredential(int credentialId, int playerId) {
        Credential found = database.getCredentials().stream()
                .filter(c -> c.getId() == credentialId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("El usuario no existe"));
        if (!found.isVerified()) {
            throw new UserNotFoundException("Usuario no verificado");
        }
        found.getJugadoresFavoritos().add(database.getPlayers().stream()
                .filter(p -> p.getId() == playerId)
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("El jugador no existe")));
    }

    public void deletePlayerForCredential(int credentialId, int playerId) {
        Credential found = database.getCredentials().stream()
                .filter(c -> c.getId() == credentialId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("El usuario no existe"));

        Player player = found.getJugadoresFavoritos().stream()
                .filter(p -> p.getId() == playerId)
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("El jugador no existe"));
        found.getJugadoresFavoritos().removeIf(p -> p.getId() == player.getId());
    }
}
