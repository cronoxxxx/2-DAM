package org.example.webexamenadriansaavedra.domain.service;

import org.example.webexamenadriansaavedra.dao.DaoUsuario;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario {
    private DaoUsuario daoUsuario;

    public ServicioUsuario(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
    }
}
