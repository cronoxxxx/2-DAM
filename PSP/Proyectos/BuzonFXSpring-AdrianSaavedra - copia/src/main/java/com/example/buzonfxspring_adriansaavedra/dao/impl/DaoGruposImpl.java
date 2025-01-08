package com.example.buzonfxspring_adriansaavedra.dao.impl;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.dao.*;
import com.example.buzonfxspring_adriansaavedra.domain.errors.*;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class DaoGruposImpl implements DaoGrupos {
    private final Database daoGrupos;

    public DaoGruposImpl(Database daoGrupos) {
        this.daoGrupos = daoGrupos;
    }


    @Override
    public Either<ErrorApp, List<String>> obtenerGruposParaUsuario(String nombreUsuario, boolean publico) {
        return daoGrupos.loadGrupos()
                .map(list -> list.stream()
                        .filter(grupo -> grupo.isPublico() == publico)
                        .filter(grupo -> grupo.getParticipantes().stream()
                                .anyMatch(participante -> participante.getNombre().equals(nombreUsuario)) ||
                                grupo.getAdministrador().getNombre().equals(nombreUsuario))
                        .map(Grupo::getNombre)
                        .toList())
                .flatMap(gruposUsuario -> gruposUsuario.isEmpty()
                        ? Either.left(new ErrorAppDatosNoValidos(Constantes.NO_SE_ENCONTRARON_GRUPOS))
                        : Either.right(gruposUsuario))
                .mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_AL_OBTENER_GRUPOS));
    }

    @Override
    public Either<ErrorApp, Grupo> obtenerGrupoPorNombre(String nombreGrupo) {
        return daoGrupos.loadGrupos()
                .flatMap(list -> list.stream()
                        .filter(g -> g != null && g.getNombre() != null && g.getNombre().equals(nombreGrupo))
                        .findFirst()
                        .map(Either::<ErrorApp, Grupo>right)
                        .orElseGet(() -> Either.left(new ErrorAppDatosNoValidos(Constantes.NO_HAY_GRUPO_ENCONTRADO))));
    }

    @Override
    public Either<ErrorApp, Boolean> agregarMiembroGrupo(Grupo grupo, Usuario miembro) {
        return daoGrupos.loadGrupos()
                .flatMap(list -> {
                    Optional<Grupo> grupoExistente = list.stream()
                            .filter(g -> g.getNombre().equals(grupo.getNombre()))
                            .findFirst();
                    if (grupoExistente.isPresent()) {
                        Grupo grupoActualizado = grupoExistente.get();
                        if (grupoActualizado.getParticipantes().stream().noneMatch(p -> p.getNombre().equals(miembro.getNombre()))) {
                            grupoActualizado.getParticipantes().add(miembro);
                            list.set(list.indexOf(grupoExistente.get()), grupoActualizado);
                            return daoGrupos.saveGrupos(list).map(saved -> true);
                        } else {
                            return Either.right(false);
                        }
                    }
                    return Either.left(new ErrorAppDatosNoValidos(Constantes.GRUPO_NO_ENCONTRADO));
                });
    }

    @Override
    public Either<ErrorApp, Boolean> addGroup(Grupo grupo) {
        return daoGrupos.loadGrupos()
                .flatMap(list -> {
                    if (list.stream().anyMatch(g -> g.getNombre().equals(grupo.getNombre()))) {
                        return Either.left(new ErrorAppDatosNoValidos(Constantes.GRUPO_YA_EXISTE));
                    }
                    if (grupo.getAdministrador() != null) {
                        grupo.getParticipantes().add(grupo.getAdministrador());
                    }
                    list.add(grupo);
                    return daoGrupos.saveGrupos(list);
                })
                .mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_AL_AGREGAR_GRUPO));
    }
    @Override
    public Either<ErrorApp, Grupo> ingresar(Grupo grupo) {
        return obtenerGrupoPorNombre(grupo.getNombre())
                .flatMap(grupoEncontrado -> {
                    if (grupoEncontrado.isPublico() != grupo.isPublico()) {
                        return Either.left(new ErrorAppDatosNoValidos(Constantes.TIPO_GRUPO_NO_COINCIDE));
                    }
                    return Either.right(grupoEncontrado);
                });
    }
}