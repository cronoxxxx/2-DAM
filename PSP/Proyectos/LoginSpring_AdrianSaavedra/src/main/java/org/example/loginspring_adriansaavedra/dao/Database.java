package org.example.loginspring_adriansaavedra.dao;

import lombok.Getter;
import org.example.loginspring_adriansaavedra.domain.model.Credential;
import org.example.loginspring_adriansaavedra.domain.model.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class Database {
    private final List<Player> players;
    private final List<Credential> credentials;

    public Database() {
        credentials = new ArrayList<>();
        players = new ArrayList<>();
        players.add(new Player(1, "Lionel Messi", "Inter Miami", "Argentina"));
        players.add(new Player(2, "Cristiano Ronaldo", "Al-Nassr", "Portugal"));
        players.add(new Player(3, "Kylian Mbappe", "Real Madrid", "France"));
        players.add(new Player(4, "Karim Benzema", "Al-Ittihad", "France"));
        players.add(new Player(5, "Robert Lewandowski", "FC Barcelona", "Poland"));
        players.add(new Player(6, "Erling Haaland", "Manchester City", "Norway"));
        players.add(new Player(7, "Kevin De Bruyne", "Manchester City", "Belgium"));
    }


}
