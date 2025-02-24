package org.example.simulacroweb.dao;

import lombok.RequiredArgsConstructor;
import org.example.simulacroweb.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class DaoUser {

    private Database db;

    public DaoUser(Database db) {
        this.db = db;
    }

    public boolean register(User usuario){

        if(ensureUserDoesNotExist(usuario.getUsuario())){
            db.getUsers().add(usuario);
            return true;
        }

        else {
            return false;
        }

    }

    private boolean ensureUserDoesNotExist(String username) {
        return db.getUsers().stream()
                .noneMatch(existingUser -> existingUser.getUsuario().equals(username));
    }


    public User findByUsername(String username) {
        return db.getUsers().stream().filter(user -> user.getUsuario().equals(username)).findFirst().orElse(null);

    }
}
