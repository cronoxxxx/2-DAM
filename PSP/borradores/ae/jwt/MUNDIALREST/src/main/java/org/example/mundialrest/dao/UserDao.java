package org.example.mundialrest.dao;


import org.example.mundialrest.common.errors.UnauthorizedOperationException;
import org.example.mundialrest.common.errors.UserAlreadyExistsException;
import org.example.mundialrest.domain.model.User;
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
                .filter(user -> user.getName().equals(username))
                .findFirst();
    }

    public void save(User user) {
        validateUserRole(user.getRole());
        ensureUserDoesNotExist(user.getName());
        database.getUsers().add(user);
    }

    private void validateUserRole(String role) {
        Set<String> validRoles = Set.of("MANAGER", "COACH", "PLAYER");
        if (!validRoles.contains(role)) {
            throw new UnauthorizedOperationException("Invalid role: " + role);
        }
    }

    private void ensureUserDoesNotExist(String username) {
        boolean userExists = database.getUsers().stream()
                .anyMatch(existingUser -> existingUser.getName().equals(username));
        if (userExists) {
            throw new UserAlreadyExistsException("User already exists: " + username);
        }
    }
}

