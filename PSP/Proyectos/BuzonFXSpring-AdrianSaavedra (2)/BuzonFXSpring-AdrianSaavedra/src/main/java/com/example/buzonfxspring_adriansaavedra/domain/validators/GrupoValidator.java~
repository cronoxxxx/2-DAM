package com.example.buzonfxspring_adriansaavedra.domain.validators;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import com.example.buzonfxspring_adriansaavedra.domain.model.Grupo;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

@Component
public class GrupoValidator {
    public Either<ErrorApp, Boolean> validateGrupo(Grupo grupo) {
        if (grupo.getNombre().isBlank()) {
            return Either.left(new ErrorAppDatosNoValidos(Constantes.CONTENIDO_ERROR_GRUPO_NO_ENCONTRADO));
        }
        if (grupo.isPublico() && (grupo.getPassword() == null || grupo.getPassword().isBlank())) {
            return Either.left(new ErrorAppDatosNoValidos(Constantes.CONTENIDO_ERROR_CAMPO_VACIO));
        }

        return Either.right(true);
    }
}
