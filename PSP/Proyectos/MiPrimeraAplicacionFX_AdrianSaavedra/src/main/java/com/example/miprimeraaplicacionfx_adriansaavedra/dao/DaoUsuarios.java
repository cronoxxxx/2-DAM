package com.example.miprimeraaplicacionfx_adriansaavedra.dao;


import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Usuario;

import java.util.List;

public interface DaoUsuarios {

    List<Usuario> obtenerUsuarios();

    boolean saveUsuarios(List<Usuario> usuarios);


    void actualizarUsuario(Usuario usuario);
    Usuario verificacion(Usuario nickname);

    boolean addUsuario(Usuario usuario);

    Usuario buscarUsuarioPorNombre(String nombre);

    List<Usuario> buscarUsuariosPorNombres(List<String> nombres);
}
