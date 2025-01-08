package com.example.buzonfxspring_adriansaavedra.dao;

import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.model.Grupo;
import com.example.buzonfxspring_adriansaavedra.domain.model.Mensaje;

import java.util.List;


import io.vavr.control.Either;

public interface DaoMensajes {

    Either<ErrorApp, List<Mensaje>> obtenerMensajesDeGrupo(Grupo grupo);

    Either<ErrorApp, Boolean> addMensajes(Mensaje mensaje);
}