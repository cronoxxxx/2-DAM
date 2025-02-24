package org.example.alumnosimulacro.dao;

import org.example.alumnosimulacro.domain.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class DaoUsers {
    private final Database db;


    public DaoUsers(Database db) {
        this.db = db;
    }

    public Optional<Usuario> findByUsername(String username) {
        return db.getUsuarios().stream()
                .filter(user -> user.getNombre().equals(username))
                .findFirst();
    }

    public void register(Usuario usuario){
        validateUserRole(usuario.getRol());
        ensureUserDoesNotExist(usuario.getNombre());
        db.getUsuarios().add(usuario);
    }

    private void validateUserRole(String role) {
        Set<String> validRoles = Set.of("PROFESOR","ALUMNO");
        if (!validRoles.contains(role)) {
            throw new RuntimeException("Invalid role: " + role);
        }
    }

    private void ensureUserDoesNotExist(String username) {
        boolean userExists = db.getUsuarios().stream()
                .anyMatch(existingUser -> existingUser.getNombre().equals(username));
        if (userExists) {
            throw new RuntimeException("User already exists: " + username);
        }
    }


}
