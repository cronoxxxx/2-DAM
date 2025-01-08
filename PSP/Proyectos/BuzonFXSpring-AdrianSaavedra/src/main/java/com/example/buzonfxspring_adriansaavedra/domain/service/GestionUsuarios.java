package com.example.buzonfxspring_adriansaavedra.domain.service;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.common.seguridad.EncriptacionAsim;
import com.example.buzonfxspring_adriansaavedra.dao.DaoUsuarios;
import com.example.buzonfxspring_adriansaavedra.domain.errors.*;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import com.example.buzonfxspring_adriansaavedra.domain.validators.UserValidator;
import io.vavr.control.Either;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class GestionUsuarios {
    private final DaoUsuarios dao;
    private final UserValidator userValidator;
    private final EncriptacionAsim encriptacionAsim;

    public GestionUsuarios(DaoUsuarios dao, UserValidator userValidator, EncriptacionAsim encriptacionAsim) {
        this.dao = dao;
        this.userValidator = userValidator;
        this.encriptacionAsim = encriptacionAsim;
    }

    @Async
    public CompletableFuture<Either<ErrorApp, Usuario>> login(Usuario usuario, String password) {
        return CompletableFuture.completedFuture(
                userValidator.validateUser(usuario)
                        .flatMap(valid -> dao.buscarUsuarioPorNombre(usuario.getNombre()))
                        .flatMap(usuarioEncontrado -> encriptacionAsim.verificarCertificadoUsuario(usuarioEncontrado.getNombre(), password)
                                .map(valid -> usuarioEncontrado))
                                .mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_LOGIN))
        );
    }


    @Async
    public CompletableFuture<Either<ErrorApp, Boolean>> register(Usuario usuario, String password) {
        return CompletableFuture.completedFuture(
                userValidator.validateUser(usuario)
                        .flatMap(valid -> encriptacionAsim.generarParClaves())
                        .flatMap(userKeyPair -> encriptacionAsim.generarCertificadoUsuario(usuario.getNombre(), userKeyPair)
                        .flatMap(userCertificate -> encriptacionAsim.almacenarClavesUsuario(usuario.getNombre(), userKeyPair, userCertificate, password))
                        .flatMap(saved -> dao.addUsuario(usuario)))
                        .mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_REGISTRO))
        );
    }


    @Async
    public CompletableFuture<Either<ErrorApp, Usuario>> buscarUsuarioPorNombre(String nombre) {
        return CompletableFuture.completedFuture(dao.buscarUsuarioPorNombre(nombre));
    }
}