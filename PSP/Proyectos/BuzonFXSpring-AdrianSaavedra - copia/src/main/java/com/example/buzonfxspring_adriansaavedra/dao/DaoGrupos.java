package com.example.buzonfxspring_adriansaavedra.dao;

import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.model.Grupo;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import io.vavr.control.Either;

import java.util.List;

public interface DaoGrupos {


    Either<ErrorApp, Boolean> addGroup(Grupo grupo);
    Either<ErrorApp, List<String>> obtenerGruposParaUsuario(String nombreUsuario, boolean publico);
    Either<ErrorApp, Grupo> obtenerGrupoPorNombre(String nombreGrupo);
    Either<ErrorApp, Boolean> agregarMiembroGrupo(Grupo grupo, Usuario miembro);
    Either<ErrorApp, Grupo> ingresar(Grupo grupo);
}
