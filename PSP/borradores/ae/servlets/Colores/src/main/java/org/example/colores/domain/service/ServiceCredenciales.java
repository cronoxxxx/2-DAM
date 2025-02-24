package org.example.colores.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.colores.dao.DaoCredenciales;
import org.example.colores.domain.model.Credential;
import org.springframework.stereotype.Service;

@Service
public class ServiceCredenciales {
    private final DaoCredenciales daoCredenciales;

    public ServiceCredenciales(DaoCredenciales daoCredenciales) {
        this.daoCredenciales = daoCredenciales;
    }

    public boolean register(Credential credential) {
        return daoCredenciales.register(credential);
    }

    public Credential login(Credential credential) {
        return daoCredenciales.login(credential);
    }
}
