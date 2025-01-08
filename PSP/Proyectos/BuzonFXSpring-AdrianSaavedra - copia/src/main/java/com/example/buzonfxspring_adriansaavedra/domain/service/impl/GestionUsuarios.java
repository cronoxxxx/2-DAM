package com.example.buzonfxspring_adriansaavedra.domain.service.impl;


import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.common.seguridad.AsimetricComponent;
import com.example.buzonfxspring_adriansaavedra.dao.impl.DaoUsuariosImpl;
import com.example.buzonfxspring_adriansaavedra.domain.errors.*;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import com.example.buzonfxspring_adriansaavedra.domain.service.IGestionUsuarios;
import com.example.buzonfxspring_adriansaavedra.domain.validators.UserValidator;
import io.vavr.control.Either;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.List;
@Log4j2
@Service
public class GestionUsuarios implements IGestionUsuarios {
    private final DaoUsuariosImpl dao;
    private final UserValidator userValidator;
    private final AsimetricComponent asimetricComponent;

    public GestionUsuarios(DaoUsuariosImpl dao, UserValidator userValidator, AsimetricComponent asimetricComponent) {
        this.dao = dao;
        this.userValidator = userValidator;
        this.asimetricComponent = asimetricComponent;
    }

    @Override
    public Either<ErrorApp, Usuario> login(Usuario usuario, String password) {
        return dao.buscarUsuarioPorNombre(usuario.getNombre())
                .flatMap(u -> {
                    Either<ErrorApp, PrivateKey> privateKeyResult = asimetricComponent.obtenerClavePrivada(usuario.getNombre(), password);
                    return privateKeyResult.map(privateKey -> {
                        // Si se pudo obtener la clave privada, el login es exitoso
                        return usuario;
                    }).mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.USUARIO_NO_VALIDO));
                });
    }

    @Override
    public Either<ErrorApp, Boolean> addUsuario(Usuario user, String password) {
        return userValidator.validateUser(new Usuario(user.getNombre(), ""))
                .flatMap(valid -> {
                    try {
                        Either<ErrorApp, KeyPair> keyPairResult = asimetricComponent.generarClaves(user.getNombre(), password);
                        return keyPairResult.flatMap(keyPair -> {
                            String clavePublicaBase64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
                            Usuario nuevoUsuario = new Usuario(user.getNombre(), clavePublicaBase64);

                            return asimetricComponent.generarCertificado(keyPair, user.getNombre())
                                    .flatMap(cert -> asimetricComponent.almacenarEnKeyStore(user.getNombre(), keyPair.getPrivate(), cert, password))
                                    .flatMap(stored -> dao.addUsuario(nuevoUsuario));
                        });
                    } catch (Exception e) {
                        log.error("Error al registrar usuario: " + e.getMessage(), e);
                        return Either.left(new ErrorAppDatosNoValidos("Error al registrar usuario: " + e.getMessage()));
                    }
                });
    }

    @Override
    public Either<ErrorApp, Usuario> buscarUsuarioPorNombre(String nombre) {
        return dao.buscarUsuarioPorNombre(nombre);
    }

    @Override
    public Either<ErrorApp, List<Usuario>> buscarUsuariosPorNombres(List<String> nombres) {
        return dao.buscarUsuariosPorNombres(nombres);
    }
}