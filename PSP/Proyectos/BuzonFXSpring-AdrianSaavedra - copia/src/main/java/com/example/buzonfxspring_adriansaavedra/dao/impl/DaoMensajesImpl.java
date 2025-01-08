package com.example.buzonfxspring_adriansaavedra.dao.impl;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.dao.DaoMensajes;
import com.example.buzonfxspring_adriansaavedra.dao.Database;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import com.example.buzonfxspring_adriansaavedra.domain.model.Grupo;
import com.example.buzonfxspring_adriansaavedra.domain.model.Mensaje;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;

@Repository
public class DaoMensajesImpl implements DaoMensajes {
    private final Database daoMensajes;

    public DaoMensajesImpl(Database mensajes) {
        this.daoMensajes = mensajes;
    }

    @Override
    public Either<ErrorApp, List<Mensaje>> obtenerMensajesDeGrupo(Grupo grupo) {
        return daoMensajes.loadMensajes()
                .map(mensajes -> mensajes.stream()
                        .filter(Objects::nonNull)
                        .filter(m -> m.getGroup() != null && !m.getGroup().isEmpty())
                        .filter(m -> m.getGroup().equals(grupo.getNombre()))
                        .toList())
                .flatMap(Either::right);
    }

    @Override
    public Either<ErrorApp, Boolean> addMensajes(Mensaje mensaje) {
        return daoMensajes.loadMensajes()
                .flatMap(mensajes -> {
                    mensajes.add(mensaje);
                    return daoMensajes.saveMensajes(mensajes);

                }) .mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_AL_AGREGAR_MENSAJE));
    }
}
