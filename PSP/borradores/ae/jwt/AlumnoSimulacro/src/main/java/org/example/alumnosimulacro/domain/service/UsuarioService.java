package org.example.alumnosimulacro.domain.service;

import org.example.alumnosimulacro.dao.DaoUsers;
import org.example.alumnosimulacro.domain.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final DaoUsers daoUsers;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(DaoUsers daoUsers, PasswordEncoder passwordEncoder) {
        this.daoUsers = daoUsers;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        daoUsers.register(usuario);
    }
}
