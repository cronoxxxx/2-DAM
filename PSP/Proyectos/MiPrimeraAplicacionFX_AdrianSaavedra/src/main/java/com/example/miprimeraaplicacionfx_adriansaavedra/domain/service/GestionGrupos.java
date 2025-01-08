package com.example.miprimeraaplicacionfx_adriansaavedra.domain.service;

import com.example.miprimeraaplicacionfx_adriansaavedra.dao.impl.DaoGruposImpl;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Grupo;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Usuario;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.validators.GrupoValidator;

import java.util.List;

public class GestionGrupos implements IGestionGrupos {
    private final DaoGruposImpl daoGrupos;
    private final GrupoValidator grupoValidator;

    public GestionGrupos() {
        this.daoGrupos = new DaoGruposImpl();
        this.grupoValidator = new GrupoValidator();
    }


    @Override
    public void actualizarGrupo(Grupo grupo) {
        daoGrupos.actualizarGrupo(grupo);
    }

    @Override
    public List<Grupo> obtenerGrupos() {
        return daoGrupos.obtenerGrupos();
    }

    @Override
    public boolean saveGrupos(List<Grupo> grupos) {
        return daoGrupos.saveGrupos(grupos);
    }

    @Override
    public List<String> obtenerGruposParaUsuario(String nombreUsuario, boolean publico) {
        return daoGrupos.obtenerGruposParaUsuario(nombreUsuario, publico);
    }
    @Override
    public Grupo obtenerGrupoPorNombre(String nombreGrupo) {
        return daoGrupos.obtenerGrupoPorNombre(nombreGrupo);
    }
    @Override
    public Grupo ingresar(Grupo grupo){
        if (grupoValidator.validateGrupo(grupo)) {
            return daoGrupos.ingresar(grupo);
        } else {
            return null;
        }
    }
    @Override
    public boolean addGroup(Grupo grupo){
        if (grupoValidator.validateGrupo(grupo)) {
            return daoGrupos.addGroup(grupo);
        } else {
            return false;
        }
    }

    @Override
    public void agregarMiembroGrupo(Grupo grupo, Usuario miembro) {
        if (grupoValidator.validateGrupo(grupo)) {
            daoGrupos.agregarMiembroGrupo(grupo, miembro);
        }

    }

}
