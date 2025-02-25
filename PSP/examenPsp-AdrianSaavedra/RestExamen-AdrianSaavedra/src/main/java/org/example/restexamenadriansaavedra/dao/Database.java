package org.example.restexamenadriansaavedra.dao;

import lombok.Data;
import org.example.restexamenadriansaavedra.domain.model.Credential;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Database {
    private final List<Credential> credentials;

    public Database(List<Credential> credentials) {
        this.credentials = credentials;
    }
}
