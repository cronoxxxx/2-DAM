package org.example.colores.dao;

import lombok.RequiredArgsConstructor;
import org.example.colores.domain.model.Credential;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class DaoCredenciales {
    private final Database database;

    public DaoCredenciales(Database database) {
        this.database = database;
    }

    public boolean register(Credential credential) {
        Optional<Credential> credential1 = database.getCredentials().stream()
                .filter(cred -> cred.getUsername().equals(credential.getUsername()))
                .findFirst();
        if (credential1.isPresent()) {
            return false;
        } else {
            database.getCredentials().add(credential);
            return true;
        }
    }
    public Credential login(Credential credential) {
        Optional<Credential> credential1 = database.getCredentials().stream()
                .filter(cred -> cred.getUsername().equals(credential.getUsername()) && cred.getPassword().equals(credential.getPassword()))
                .findFirst();
        return credential1.orElse(null);
    }
}
