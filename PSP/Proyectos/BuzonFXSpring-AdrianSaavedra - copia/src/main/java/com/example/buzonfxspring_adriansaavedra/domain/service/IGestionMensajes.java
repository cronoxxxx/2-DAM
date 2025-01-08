package com.example.buzonfxspring_adriansaavedra.domain.service;

import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.model.Grupo;
import com.example.buzonfxspring_adriansaavedra.domain.model.Mensaje;
import io.vavr.control.Either;

import java.util.List;

public interface IGestionMensajes {
    Either<ErrorApp, List<Mensaje>> obtenerMensajesDeGrupo(Grupo grupo);

    Either<ErrorApp, Boolean> addMensajes(Mensaje mensaje, Grupo grupo);
}
