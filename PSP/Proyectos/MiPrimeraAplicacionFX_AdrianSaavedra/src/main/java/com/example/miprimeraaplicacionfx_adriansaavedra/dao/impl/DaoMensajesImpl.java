package com.example.miprimeraaplicacionfx_adriansaavedra.dao.impl;

import com.example.miprimeraaplicacionfx_adriansaavedra.dao.DaoMensajes;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Grupo;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Mensaje;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Usuario;

import java.util.List;
import java.util.Objects;

public class DaoMensajesImpl implements DaoMensajes {
    private final Mensajes mensajeria;

    public DaoMensajesImpl() {
        this.mensajeria = new Mensajes();
    }
    @Override
    public List<Mensaje> obtenerMensajes() {
        return mensajeria.getMensajeList();
    }

    @Override
    public boolean saveMensajes(List<Mensaje> mensajes) {
        return mensajeria.saveMensajes(mensajes);
    }
    @Override
    public List<Mensaje> obtenerMensajesParaUsuario(Usuario usuario) {
        return obtenerMensajes().stream()
                .filter(Objects::nonNull)
                .filter(mensaje -> mensaje.getDestinatarios().stream()
                        .anyMatch(destinatario -> destinatario.getNombre().equals(usuario.getNombre())))
                .toList();
    }
    @Override
    public List<Mensaje> obtenerMensajesDeGrupo(Grupo grupo) {
        return obtenerMensajes().stream()
                .filter(Objects::nonNull)
                .filter(m -> m.getGroup() != null && !m.getGroup().isEmpty())
                .filter(m -> m.getGroup().equals(grupo.getNombre()))
                .toList();
    }

    @Override
    public boolean addMensajes(Mensaje mensaje) {
        obtenerMensajes().add(mensaje);
        return saveMensajes(obtenerMensajes());
    }

    @Override
    public List<Mensaje> obtenerMensajesPorGrupo(Grupo grupo) {
        return obtenerMensajes().stream()
                .filter(Objects::nonNull)
                .filter(m -> m.getGroup() != null && !m.getGroup().isEmpty())
                .filter(m -> m.getGroup().equals(grupo.getNombre()))
                .toList();
    }
}
