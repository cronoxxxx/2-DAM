package org.example.loginspring_adriansaavedra.dao;

import org.example.loginspring_adriansaavedra.common.errors.PlayerAlreadyExistsException;
import org.example.loginspring_adriansaavedra.common.errors.PlayerNotFoundException;
import org.example.loginspring_adriansaavedra.common.errors.UserNotFoundException;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class DaoJugadoresFavoritos {
    private final Database database;

    public DaoJugadoresFavoritos(Database database) {
        this.database = database;
    }

    public List<Player> getPlayersForCredential(int credentialId) {

        List<Player> lista = database.getCredentials().stream()
                .filter(c -> c.getId() == credentialId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("El usuario no existe"))
                .getJugadoresFavoritos();

        if (lista == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(lista);

    }

    public Player getPlayerForCredential(int credentialId, int playerId) {
        Credential found = database.getCredentials().stream()
                .filter(c -> c.getId() == credentialId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("El usuario no existe"));

        if (found.getJugadoresFavoritos() == null) {
            found.setJugadoresFavoritos(new ArrayList<>());
        }

        return found.getJugadoresFavoritos().stream()
                .filter(p -> p.getId() == playerId)
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("El jugador no existe"));

    }

    public void addPlayerForCredential(int credentialId, String playerName) {
        Credential found = database.getCredentials().stream()
                .filter(c -> c.getId() == credentialId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("El usuario no existe"));

        if (!found.isVerified()) {
            throw new UserNotFoundException("Usuario no verificado");
        }
        if (found.getJugadoresFavoritos() == null) {
            found.setJugadoresFavoritos(new ArrayList<>());
        }

        Player player = database.getPlayers().stream()
                .filter(p -> p.getName().equalsIgnoreCase(playerName))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("El jugador no existe"));

        if (found.getJugadoresFavoritos().stream().anyMatch(p -> p.getId() == player.getId())) {
            throw new PlayerAlreadyExistsException("El jugador ya está en favoritos");
        }

        found.getJugadoresFavoritos().add(player);
    }


    public void deletePlayerForCredential(int credentialId, int playerId) {
        Credential found = database.getCredentials().stream()
                .filter(c -> c.getId() == credentialId)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("El usuario no existe"));
        if (found.getJugadoresFavoritos() == null) {
            found.setJugadoresFavoritos(new ArrayList<>());
        }
        Player player = found.getJugadoresFavoritos().stream()
                .filter(p -> p.getId() == playerId)
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("El jugador no existe"));
        found.getJugadoresFavoritos().removeIf(p -> p.getId() == player.getId());
    }
}
