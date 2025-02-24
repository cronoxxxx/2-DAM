package org.example.restadriansaavedra.dao;

import org.example.restadriansaavedra.common.errors.ReportNotFoundException;
import org.example.restadriansaavedra.common.errors.UserAlreadyExistsException;
import org.example.restadriansaavedra.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class UserDao {
    private final Database database;

    public UserDao(Database database) {
        this.database = database;
    }

    public Optional<User> findByUsername(String username) {
        return database.getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public void save(User user) {
        validateUserRole(user.getRole());
        ensureUserDoesNotExist(user.getUsername());
        database.getUsers().add(user);
    }

    private void validateUserRole(String role) {
        Set<String> validRoles = Set.of("ADMIN", "NIVEL1", "NIVEL2");
        if (!validRoles.contains(role)) {
            throw new ReportNotFoundException("Invalid role: " + role);
        }
    }

    private void ensureUserDoesNotExist(String username) {
        boolean userExists = database.getUsers().stream()
                .anyMatch(existingUser -> existingUser.getUsername().equals(username));
        if (userExists) {
            throw new UserAlreadyExistsException("User already exists: " + username);
        }
    }

}