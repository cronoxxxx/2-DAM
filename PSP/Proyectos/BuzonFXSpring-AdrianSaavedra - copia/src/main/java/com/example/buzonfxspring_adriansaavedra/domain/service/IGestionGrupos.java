package com.example.buzonfxspring_adriansaavedra.domain.service;

import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.model.Grupo;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import io.vavr.control.Either;


import java.util.List;

public interface IGestionGrupos {
    Either<ErrorApp, List<String>> obtenerGruposParaUsuario(String nombreUsuario, boolean publico);
    Either<ErrorApp, Grupo> obtenerGrupoPorNombre(String nombreGrupo);
    Either<ErrorApp, Grupo> ingresar(Grupo grupo);
    Either<ErrorApp, Boolean> addGroup(Grupo grupo);
    Either<ErrorApp, Grupo> agregarMiembroGrupo(Grupo grupo, Usuario miembro);
}
