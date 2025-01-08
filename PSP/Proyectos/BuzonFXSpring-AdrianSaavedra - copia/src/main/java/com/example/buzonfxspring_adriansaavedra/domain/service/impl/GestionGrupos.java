package com.example.buzonfxspring_adriansaavedra.domain.service.impl;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.dao.impl.DaoGruposImpl;
import com.example.buzonfxspring_adriansaavedra.domain.errors.*;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
import com.example.buzonfxspring_adriansaavedra.domain.service.IGestionGrupos;
import com.example.buzonfxspring_adriansaavedra.domain.validators.GrupoValidator;
import io.vavr.control.Either;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionGrupos implements IGestionGrupos {
    private final DaoGruposImpl daoGrupos;
    private final GrupoValidator grupoValidator;
    private final PasswordEncoder passwordEncoder;

    public GestionGrupos(DaoGruposImpl daoGrupos, GrupoValidator grupoValidator, PasswordEncoder passwordEncoder) {
        this.daoGrupos = daoGrupos;
        this.grupoValidator = grupoValidator;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Either<ErrorApp, List<String>> obtenerGruposParaUsuario(String nombreUsuario, boolean publico) {
        return daoGrupos.obtenerGruposParaUsuario(nombreUsuario, publico);
    }

    @Override
    public Either<ErrorApp, Grupo> obtenerGrupoPorNombre(String nombreGrupo) {
        return daoGrupos.obtenerGrupoPorNombre(nombreGrupo);
    }

    @Override
    public Either<ErrorApp, Boolean> addGroup(Grupo grupo) {
        return grupoValidator.validateGrupo(grupo)
                .flatMap(valid -> {
                    if (grupo.isPublico() && grupo.getPassword() != null) {
                        grupo.setPassword(passwordEncoder.encode(grupo.getPassword()));
                    }
                    return daoGrupos.addGroup(grupo);
                });
    }

    @Override
    public Either<ErrorApp, Grupo> ingresar(Grupo grupo) {
        return daoGrupos.ingresar(grupo)
                .flatMap(grupoEncontrado -> {
                    if (grupoEncontrado.isPublico() && grupoEncontrado.getPassword() != null) {
                        if (passwordEncoder.matches(grupo.getPassword(), grupoEncontrado.getPassword())) {
                            return Either.right(grupoEncontrado);
                        } else {
                            return Either.left(new ErrorAppDatosNoValidos(Constantes.CONTRASENA_INCORRECTA));
                        }
                    }
                    return Either.right(grupoEncontrado);
                });
    }


    @Override
    public Either<ErrorApp, Grupo> agregarMiembroGrupo(Grupo grupo, Usuario miembro) {
        return grupoValidator.validateGrupo(grupo)
                .flatMap(valid -> daoGrupos.agregarMiembroGrupo(grupo, miembro))
                .flatMap(agregado -> daoGrupos.obtenerGrupoPorNombre(grupo.getNombre()));
    }
}