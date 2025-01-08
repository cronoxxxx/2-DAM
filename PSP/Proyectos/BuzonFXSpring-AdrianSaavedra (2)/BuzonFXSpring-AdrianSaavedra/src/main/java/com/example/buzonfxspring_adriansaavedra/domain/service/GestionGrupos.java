package com.example.buzonfxspring_adriansaavedra.domain.service;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.dao.DaoGrupos;
import com.example.buzonfxspring_adriansaavedra.domain.errors.*;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
import com.example.buzonfxspring_adriansaavedra.domain.validators.*;
import io.vavr.control.Either;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class GestionGrupos {
    private final DaoGrupos daoGrupos;
    private final GrupoPublicoValidator grupoPublicoValidator;
    private final GrupoPrivadoValidator grupoPrivadoValidator;
    private final PasswordEncoder passwordEncoder;

    public GestionGrupos(DaoGrupos daoGrupos, GrupoPublicoValidator grupoPublicoValidator, GrupoPrivadoValidator grupoPrivadoValidator, PasswordEncoder passwordEncoder) {
        this.grupoPrivadoValidator = grupoPrivadoValidator;
        this.daoGrupos = daoGrupos;
        this.grupoPublicoValidator = grupoPublicoValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Async
    public CompletableFuture<Either<ErrorApp, List<String>>> obtenerGruposParaUsuario(Usuario nombreUsuario, boolean publico) {
        return CompletableFuture.completedFuture(daoGrupos.obtenerGruposParaUsuario(nombreUsuario, publico));
    }

    @Async
    public CompletableFuture<Either<ErrorApp, GrupoPrivado>> obtenerGrupoPrivadoPorNombre(String nombreGrupo) {
        return CompletableFuture.completedFuture(daoGrupos.obtenerGrupoPrivadoPorNombre(nombreGrupo));
    }

    @Async
    public CompletableFuture<Either<ErrorApp, Boolean>> registrarGrupoPublico(GrupoPublico grupoPublico) {
        return CompletableFuture.completedFuture(
                grupoPublicoValidator.validateGrupo(grupoPublico)
                        .flatMap(valid -> {
                            if (grupoPublico.getPassword() != null) {
                                grupoPublico.setPassword(passwordEncoder.encode(grupoPublico.getPassword()));
                            }
                            return daoGrupos.registrarGrupoPublico(grupoPublico);
                        })
        );
    }

    @Async
    public CompletableFuture<Either<ErrorApp, Boolean>> registrarGrupoPrivado(GrupoPrivado grupoPrivado) {
        return CompletableFuture.completedFuture(
                grupoPrivadoValidator.validateGrupo(grupoPrivado)
                        .flatMap(valid -> daoGrupos.registrarGrupoPrivado(grupoPrivado))
        );
    }

    @Async
    public CompletableFuture<Either<ErrorApp, GrupoPublico>> loginGrupoPublico(String nombreGrupo, String password, Usuario usuario) {
        return CompletableFuture.completedFuture(
                daoGrupos.obtenerGrupoPublicoPorNombre(nombreGrupo)
                        .flatMap(grupo -> {
                            if (passwordEncoder.matches(password, grupo.getPassword())) {
                                if (grupo.getParticipantes().contains(usuario)) {
                                    return Either.right(grupo);
                                } else {
                                    return daoGrupos.agregarMiembroGrupoPublico(grupo, usuario)
                                            .flatMap(agregado -> Either.right(grupo));
                                }
                            } else {
                                return Either.left(new ErrorAppDatosNoValidos(Constantes.CONTRASENA_INCORRECTA));
                            }
                        })
                        .mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_INGRESAR_GRUPO_PUBLICO))
        );
    }

    @Async
    public CompletableFuture<Either<ErrorApp, Boolean>> agregarMiembroGrupoPrivado(GrupoPrivado grupoPrivado, Usuario miembro) {
        return CompletableFuture.completedFuture(
                grupoPrivadoValidator.validateGrupo(grupoPrivado)
                        .flatMap(valid -> daoGrupos.agregarMiembroGrupoPrivado(grupoPrivado, miembro))
        );
    }
}