package com.example.miprimeraaplicacionfx_adriansaavedra.dao;

import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Grupo;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Mensaje;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Usuario;

import java.util.List;

public interface DaoMensajes {

    List<Mensaje> obtenerMensajes();

    boolean saveMensajes(List<Mensaje> mensajes);
    List<Mensaje> obtenerMensajesParaUsuario(Usuario usuario);

    List<Mensaje> obtenerMensajesDeGrupo(Grupo grupo);

    boolean addMensajes(Mensaje mensaje);

    List<Mensaje> obtenerMensajesPorGrupo(Grupo grupo);
}
