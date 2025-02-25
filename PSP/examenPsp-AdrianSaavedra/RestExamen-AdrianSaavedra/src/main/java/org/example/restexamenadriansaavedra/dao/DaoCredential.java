package org.example.restexamenadriansaavedra.dao;

import org.example.restexamenadriansaavedra.common.errors.AlreadyExists;
import org.example.restexamenadriansaavedra.common.errors.Rol;
import org.example.restexamenadriansaavedra.domain.model.Credential;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class DaoCredential {
    private final Database credentials;

    public DaoCredential(Database database) {
        this.credentials = database;
    }

    public Optional<Credential> findByUsername(String username) {
        return credentials.getCredentials().stream()
                .filter(user -> user.getName().equals(username))
                .findFirst();
    }

    public void save(Credential user) {
        validateUserRole(user.getRole());
        ensureUserDoesNotExist(user.getName());
        credentials.getCredentials().add(user);
    }

    private void validateUserRole(String role) {
        Set<String> validRoles = Set.of("ADMIN", "MEXICANO", "COLOMBIANO");
        if (!validRoles.contains(role)) {
            throw new Rol("Invalid role: " + role);
        }
    }

    private void ensureUserDoesNotExist(String username) {
        boolean userExists = credentials.getCredentials().stream()
                .anyMatch(existingUser -> existingUser.getName().equals(username));
        if (userExists) {
            throw new AlreadyExists("User already exists: " + username);
        }
    }
}
