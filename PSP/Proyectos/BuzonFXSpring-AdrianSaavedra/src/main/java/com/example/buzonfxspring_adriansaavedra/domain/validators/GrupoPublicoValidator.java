package com.example.buzonfxspring_adriansaavedra.domain.validators;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import com.example.buzonfxspring_adriansaavedra.domain.model.GrupoPublico;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

@Component
public class GrupoPublicoValidator {
    public Either<ErrorApp, Boolean> validateGrupo(GrupoPublico grupoPublico) {
        if (grupoPublico.getNombre().isBlank()) {
            return Either.left(new ErrorAppDatosNoValidos(Constantes.CONTENIDO_ERROR_GRUPO_NO_ENCONTRADO));
        }
        if (grupoPublico.getPassword().isBlank()) {
            return Either.left(new ErrorAppDatosNoValidos(Constantes.CONTENIDO_ERROR_CAMPO_VACIO));
        }

        return Either.right(true);
    }
}
