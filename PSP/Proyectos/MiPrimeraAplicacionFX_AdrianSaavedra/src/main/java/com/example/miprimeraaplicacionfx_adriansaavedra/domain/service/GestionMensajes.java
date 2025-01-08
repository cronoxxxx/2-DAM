package com.example.miprimeraaplicacionfx_adriansaavedra.domain.service;

import com.example.miprimeraaplicacionfx_adriansaavedra.dao.impl.DaoMensajesImpl;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Grupo;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Mensaje;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Usuario;

import java.util.List;

public class GestionMensajes implements IGestionMensajes {
    private final DaoMensajesImpl daoMensajes;

    public GestionMensajes() {
        this.daoMensajes = new DaoMensajesImpl();
    }

    @Override
    public List<Mensaje> obtenerMensajes() {
        return daoMensajes.obtenerMensajes();
    }

    @Override
    public boolean saveMensajes(List<Mensaje> mensajes) {
        return daoMensajes.saveMensajes(mensajes);
    }

    @Override
    public List<Mensaje> obtenerMensajesParaUsuario(Usuario usuario) {
        return daoMensajes.obtenerMensajesParaUsuario(usuario);
    }

    @Override
    public List<Mensaje> obtenerMensajesDeGrupo(Grupo grupo) {
        return daoMensajes.obtenerMensajesDeGrupo(grupo);
    }

    @Override
    public boolean addMensajes(Mensaje mensaje) {
        return daoMensajes.addMensajes(mensaje);
    }
    @Override
    public List<Mensaje> obtenerMensajesPorGrupo(Grupo grupo) {
        return daoMensajes.obtenerMensajesPorGrupo(grupo);
    }
}
