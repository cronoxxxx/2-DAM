package com.example.miprimeraaplicacionfx_adriansaavedra.dao.impl;
import com.example.miprimeraaplicacionfx_adriansaavedra.dao.DaoUsuarios;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Usuario;

import java.util.List;
import java.util.Objects;

public class DaoUsuariosImpl implements DaoUsuarios {
    private final Usuarios usuarios;
    public DaoUsuariosImpl() {
        this.usuarios = new Usuarios();
    }
    @Override
    public List<Usuario> obtenerUsuarios() {
        return usuarios.getUsuarioList();
    }


    @Override
    public Usuario verificacion(Usuario nickname) {
        saveUsuarios(obtenerUsuarios());
        return usuarios.getUsuarioList().stream(
                ).filter(Objects::nonNull)
                .filter(u -> u.getNombre().equals(nickname.getNombre()) && u.getPassword().equals(nickname.getPassword()))
                .findFirst()
                .orElse(null
        );
    }

    @Override
    public boolean saveUsuarios(List<Usuario> usuarios) {
        return this.usuarios.saveUsuarios(usuarios);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        usuarios.getUsuarioList().stream()
              .filter(u -> u.getNombre().equals(usuario.getNombre()))
                .findFirst()
                .ifPresent(u -> {
                    int index = usuarios.getUsuarioList().indexOf(u);
                    usuarios.getUsuarioList().set(index, usuario);
                });
        saveUsuarios(usuarios.getUsuarioList());
    }
    @Override
    public boolean addUsuario(Usuario usuario) {
        obtenerUsuarios().add(usuario);
        return saveUsuarios(obtenerUsuarios());
    }

    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) {
        saveUsuarios(obtenerUsuarios());

        return obtenerUsuarios().stream()
                .filter(u -> u != null && u.getNombre() != null)
                .filter(u -> u.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }
    @Override
    public List<Usuario> buscarUsuariosPorNombres(List<String> nombres) {
        saveUsuarios(obtenerUsuarios());

        return obtenerUsuarios().stream()
                .filter(usuario -> usuario != null && usuario.getNombre() != null)
                .filter(usuario -> nombres.stream()
                        .anyMatch(nombre -> nombre.equals(usuario.getNombre())))
                .toList();
    }

}
