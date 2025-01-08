package com.example.buzonfxspring_adriansaavedra.dao;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.domain.errors.*;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class DaoGrupos {
    private final Database database;

    public DaoGrupos(Database database) {
        this.database = database;
    }


    public Either<ErrorApp, List<String>> obtenerGruposParaUsuario(Usuario nombreUsuario, boolean publico) {
        if (publico) {
            return database.loadGruposPublicos()
                    .map(list -> list.stream()
                            .filter(grupo -> grupo.getParticipantes().stream()
                                    .anyMatch(participante -> participante.equals(nombreUsuario)) ||
                                    grupo.getAdministrador().equals(nombreUsuario))
                            .map(GrupoPublico::getNombre)
                            .toList())
                    .flatMap(Either::right);
        } else {
            return database.loadGruposPrivados()
                    .map(list -> list.stream()
                            .filter(grupo -> grupo.getParticipantes().contains(nombreUsuario) ||
                                    grupo.getAdministrador().equals(nombreUsuario))
                            .map(GrupoPrivado::getNombre)
                            .toList())
                    .flatMap(Either::right);
        }
    }


    public Either<ErrorApp, GrupoPublico> obtenerGrupoPublicoPorNombre(String nombreGrupo) {
        return database.loadGruposPublicos()
                .flatMap(list -> list.stream()
                        .filter(g -> g.getNombre().equals(nombreGrupo))
                        .findFirst()
                        .map(Either::<ErrorApp, GrupoPublico>right)
                        .orElseGet(() -> Either.left(new ErrorAppDatosNoValidos(Constantes.NO_HAY_GRUPO_ENCONTRADO))));
    }


    public Either<ErrorApp, GrupoPrivado> obtenerGrupoPrivadoPorNombre(String nombreGrupo) {
        return database.loadGruposPrivados()
                .flatMap(list -> list.stream()
                        .filter(g -> g.getNombre().equals(nombreGrupo))
                        .findFirst()
                        .map(Either::<ErrorApp, GrupoPrivado>right)
                        .orElseGet(() -> Either.left(new ErrorAppDatosNoValidos(Constantes.NO_HAY_GRUPO_ENCONTRADO))));
    }


    public Either<ErrorApp, Boolean> agregarMiembroGrupoPublico(GrupoPublico grupoPublico, Usuario miembro) {
        return database.loadGruposPublicos()
                .flatMap(list -> {
                    Optional<GrupoPublico> grupoExistente = list.stream()
                            .filter(g -> g.getNombre().equals(grupoPublico.getNombre()))
                            .findFirst();
                    if (grupoExistente.isPresent()) {
                        GrupoPublico grupoActualizado = grupoExistente.get();
                        if (!grupoActualizado.getParticipantes().contains(miembro)) {
                            grupoActualizado.getParticipantes().add(miembro);
                            list.set(list.indexOf(grupoExistente.get()), grupoActualizado);
                            return database.saveGruposPublicos(list)
                                    .map(success -> true);
                        } else {
                            return Either.right(true);
                        }
                    }
                    return Either.left(new ErrorAppDatosNoValidos(Constantes.GRUPO_NO_ENCONTRADO));
                });
    }


    public Either<ErrorApp, Boolean> agregarMiembroGrupoPrivado(GrupoPrivado grupoPrivado, Usuario miembro) {
        return database.loadGruposPrivados()
                .flatMap(list -> {
                    Optional<GrupoPrivado> grupoExistente = list.stream()
                            .filter(g -> g.getNombre().equals(grupoPrivado.getNombre()))
                            .findFirst();
                    if (grupoExistente.isPresent()) {
                        GrupoPrivado grupoActualizado = grupoExistente.get();
                        if (!grupoActualizado.getParticipantes().contains(miembro)) {
                            grupoActualizado.getParticipantes().add(miembro);
                            list.set(list.indexOf(grupoExistente.get()), grupoActualizado);
                            return database.saveGruposPrivados(list);
                        } else {
                            return Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_YA_EN_GRUPO));
                        }
                    }
                    return Either.left(new ErrorAppDatosNoValidos(Constantes.GRUPO_NO_ENCONTRADO));
                });
    }


    public Either<ErrorApp, Boolean> registrarGrupoPublico(GrupoPublico grupoPublico) {
        return database.loadGruposPublicos()
                .flatMap(list -> {
                    if (list.stream().anyMatch(g -> g.getNombre().equals(grupoPublico.getNombre()))) {
                        return Either.left(new ErrorAppDatosNoValidos(Constantes.GRUPO_YA_EXISTE));
                    }
                    if (grupoPublico.getAdministrador() != null) {
                        grupoPublico.getParticipantes().add(grupoPublico.getAdministrador());
                    }
                    list.add(grupoPublico);
                    return database.saveGruposPublicos(list);
                })
                .mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_AL_AGREGAR_GRUPO));
    }

    public Either<ErrorApp, Boolean> registrarGrupoPrivado(GrupoPrivado grupoPrivado) {
        return database.loadGruposPrivados()
                .flatMap(list -> {
                    if (list.stream().anyMatch(g -> g.getNombre().equals(grupoPrivado.getNombre()))) {
                        return Either.left(new ErrorAppDatosNoValidos(Constantes.GRUPO_YA_EXISTE));
                    }
                    if (grupoPrivado.getAdministrador() != null) {
                        grupoPrivado.getParticipantes().add(grupoPrivado.getAdministrador());
                    }
                    list.add(grupoPrivado);
                    return database.saveGruposPrivados(list);
                })
                .mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_AL_AGREGAR_GRUPO));
    }
}