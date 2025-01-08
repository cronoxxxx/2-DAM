package com.example.buzonfxspring_adriansaavedra.dao.impl;
import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.dao.DaoUsuarios;
import com.example.buzonfxspring_adriansaavedra.dao.Database;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class DaoUsuariosImpl implements DaoUsuarios {
    private final Database daoUsuarios;

    public DaoUsuariosImpl(Database daoUsuarios) {
        this.daoUsuarios = daoUsuarios;
    }

    @Override
    public Either<ErrorApp, Usuario> buscarUsuarioPorNombre(String nombre) {
        return daoUsuarios.loadUsuarios()
                .flatMap(users -> users.stream()
                        .filter(u -> u != null && u.getNombre() != null && u.getNombre().equals(nombre))
                        .findFirst()
                        .map(Either::<ErrorApp, Usuario>right)
                        .orElseGet(() -> Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_NO_ENCONTRADO)))
                );
    }

    @Override
    public Either<ErrorApp, List<Usuario>> buscarUsuariosPorNombres(List<String> nombres) {
        return daoUsuarios.loadUsuarios()
                .map(users -> users.stream()
                        .filter(u -> u != null && u.getNombre() != null && nombres.contains(u.getNombre()))
                        .toList())
                .flatMap(foundUsers -> foundUsers.isEmpty()
                        ? Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIOS_NO_ENCONTRADOS))
                        : Either.right(foundUsers));
    }

    @Override
    public Either<ErrorApp, Boolean> addUsuario(Usuario usuario) {
        return daoUsuarios.loadUsuarios()
                .flatMap(users -> {
                    if (users.stream().anyMatch(u -> u.getNombre().equals(usuario.getNombre()))) {
                        return Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_YA_EXISTE));
                    }
                    users.add(usuario);
                    return daoUsuarios.saveUsuarios(users);
                });
    }
}
