package org.example.restexamenadriansaavedra.domain.service;

import org.example.restexamenadriansaavedra.dao.DaoUsuario;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario {
    private DaoUsuario daoUsuario;

    public ServicioUsuario(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
    }
}
