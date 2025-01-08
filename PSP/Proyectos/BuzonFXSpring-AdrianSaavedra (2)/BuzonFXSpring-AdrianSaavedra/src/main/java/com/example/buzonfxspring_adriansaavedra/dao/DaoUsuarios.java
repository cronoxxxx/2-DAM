package com.example.buzonfxspring_adriansaavedra.dao;
import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

@Repository
public class DaoUsuarios {
    private final Database database;

    public DaoUsuarios(Database database) {
        this.database = database;
    }

      
    public Either<ErrorApp, Usuario> buscarUsuarioPorNombre(String nombre) {
        return database.loadUsuarios()
                .flatMap(users -> users.stream()
                        .filter(u -> u != null && u.getNombre() != null && u.getNombre().equals(nombre))
                        .findFirst()
                        .map(Either::<ErrorApp, Usuario>right)
                        .orElseGet(() -> Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_NO_ENCONTRADO)))
                );
    }

      
    public Either<ErrorApp, Boolean> addUsuario(Usuario usuario) {
        return database.loadUsuarios()
                .flatMap(users -> {
                    if (users.stream().anyMatch(u -> u.getNombre().equals(usuario.getNombre()))) {
                        return Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_YA_EXISTE));
                    }
                    users.add(usuario);
                    return database.saveUsuarios(users);
                });
    }
}
