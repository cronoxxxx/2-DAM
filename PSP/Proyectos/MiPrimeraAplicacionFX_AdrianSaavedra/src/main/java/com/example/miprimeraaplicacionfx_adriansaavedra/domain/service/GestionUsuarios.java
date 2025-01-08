package com.example.miprimeraaplicacionfx_adriansaavedra.domain.service;

import com.example.miprimeraaplicacionfx_adriansaavedra.dao.impl.DaoUsuariosImpl;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Usuario;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.validators.UserValidator;

import java.util.List;

public class GestionUsuarios implements IGestionUsuarios {
    private final DaoUsuariosImpl dao;
    private final UserValidator userValidator;

    public GestionUsuarios() {
        this.dao = new DaoUsuariosImpl();
        this.userValidator = new UserValidator();
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return dao.obtenerUsuarios();
    }


    @Override
    public boolean saveUsuarios(List<Usuario> usuarios) {
        return dao.saveUsuarios(usuarios);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (userValidator.validateUser(usuario)) {
            dao.actualizarUsuario(usuario);
        }
    }

    @Override
    public Usuario verificacion(Usuario nickname) {
        if (userValidator.validateUser(nickname)) {
            return dao.verificacion(nickname);
        } else {
            return null;
        }
    }

    @Override
    public boolean addUsuario(Usuario usuario) {

        if (userValidator.validateUser(usuario)) {
            return dao.addUsuario(usuario);
        } else {
            return false;
        }

    }

    @Override
    public Usuario buscarUsuarioPorNombre(String nombre) {
        return dao.buscarUsuarioPorNombre(nombre);
    }

    @Override
    public List<Usuario> buscarUsuariosPorNombres(List<String> nombres) {
        return dao.buscarUsuariosPorNombres(nombres);
    }
}