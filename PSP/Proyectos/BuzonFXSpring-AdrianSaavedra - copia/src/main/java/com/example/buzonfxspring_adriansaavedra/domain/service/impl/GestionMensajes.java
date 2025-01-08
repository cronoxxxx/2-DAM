package com.example.buzonfxspring_adriansaavedra.domain.service.impl;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.common.seguridad.EncriptacionAES;
import com.example.buzonfxspring_adriansaavedra.dao.impl.DaoMensajesImpl;
import com.example.buzonfxspring_adriansaavedra.domain.errors.*;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
import com.example.buzonfxspring_adriansaavedra.domain.service.IGestionMensajes;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GestionMensajes implements IGestionMensajes {
    private final DaoMensajesImpl daoMensajes;
    private final EncriptacionAES encriptacion;

    public GestionMensajes(DaoMensajesImpl daoMensajes, EncriptacionAES encriptacion) {
        this.encriptacion = encriptacion;
        this.daoMensajes = daoMensajes;
    }


    @Override
    public Either<ErrorApp, List<Mensaje>> obtenerMensajesDeGrupo(Grupo grupo) {
        return daoMensajes.obtenerMensajesDeGrupo(grupo)
                .flatMap(mensajes -> {
                    if (grupo.isPublico() && grupo.getPassword() != null) {
                        return desencriptarMensajes(mensajes, grupo.getPassword());
                    } else {
                        return Either.right(mensajes);
                    }
                });
    }

    @Override
    public Either<ErrorApp, Boolean> addMensajes(Mensaje mensaje, Grupo grupo) {
        if (grupo.isPublico()) {
            if (grupo.getPassword() == null) {
                return Either.left(new ErrorAppDatosNoValidos("El grupo público debe tener una contraseña."));
            }
            return encriptacion.encriptar(mensaje.getTexto(), grupo.getPassword())
                    .flatMap(textoEncriptado -> {
                        mensaje.setTexto(textoEncriptado);
                        return daoMensajes.addMensajes(mensaje);
                    }).mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_AL_AGREGAR_MENSAJE));
        } else {
            return daoMensajes.addMensajes(mensaje);
        }
    }

    private Either<ErrorApp, Mensaje> desencriptarMensaje(Mensaje mensaje, String password) {
        return encriptacion.desencriptar(mensaje.getTexto(), password)
                .map(contenidoDesencriptado -> {
                    mensaje.setTexto(contenidoDesencriptado);
                    return mensaje;
                }).mapLeft(error -> new ErrorAppDatosNoValidos(Constantes.ERROR_DESENCRIP_MENSAJES));
    }

    private Either<ErrorApp, List<Mensaje>> desencriptarMensajes(List<Mensaje> mensajes, String password) {
        List<Either<ErrorApp, Mensaje>> desencriptados = mensajes.stream()
                .map(mensaje -> desencriptarMensaje(mensaje, password))
                .toList();
        return desencriptados.stream().allMatch(Either::isRight)
                ? Either.right(desencriptados.stream()
                .map(Either::get)
                .toList())
                : Either.left(new ErrorAppDatosNoValidos(Constantes.ERROR_DESENCRIP_MENSAJES));
    }
}
