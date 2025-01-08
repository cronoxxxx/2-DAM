package com.example.buzonfxspring_adriansaavedra.domain.service;

import com.example.buzonfxspring_adriansaavedra.domain.model.Grupo;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;


import java.util.List;

public interface IGestionGrupos {


    boolean actualizarGrupo(Grupo grupo);

    List<Grupo> obtenerGrupos();

    boolean saveGrupos(List<Grupo> grupos);
    List<String> obtenerGruposParaUsuario(String nombreUsuario, boolean publico);


    Grupo obtenerGrupoPorNombre(String nombreGrupo);

    Grupo ingresar(Grupo grupo);

    boolean addGroup(Grupo grupo);
    boolean agregarMiembroGrupo(Grupo grupo, Usuario miembro);
}
