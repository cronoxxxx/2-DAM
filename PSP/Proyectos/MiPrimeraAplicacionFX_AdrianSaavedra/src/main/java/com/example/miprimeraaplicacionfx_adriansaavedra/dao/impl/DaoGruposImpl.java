package com.example.miprimeraaplicacionfx_adriansaavedra.dao.impl;

import com.example.miprimeraaplicacionfx_adriansaavedra.dao.DaoGrupos;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Grupo;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Usuario;

import java.util.List;



public class DaoGruposImpl implements DaoGrupos {
    private final Grupos grupos;

    public DaoGruposImpl() {
        this.grupos = new Grupos();
    }


    @Override
    public void actualizarGrupo(Grupo grupo) {
        obtenerGrupos().stream()
                .filter(g -> g.getNombre().equals(grupo.getNombre()))
                .findFirst()
                .ifPresent(g -> {
                    int index = grupos.getGrupoList().indexOf(g);
                    grupos.getGrupoList().set(index, grupo);
                });
        saveGrupos(grupos.getGrupoList());
    }

    @Override
    public List<Grupo> obtenerGrupos() {
        return grupos.getGrupoList();
    }

    @Override
    public boolean saveGrupos(List<Grupo> grupos) {
        return this.grupos.saveGrupos(grupos);
    }
    @Override
    public List<String> obtenerGruposParaUsuario(String nombreUsuario, boolean publico) {
        saveGrupos(obtenerGrupos());
        return obtenerGrupos().stream()
                .filter(grupo -> grupo.isPublico() == publico)
                .filter(grupo -> grupo.getParticipantes().stream()
                        .anyMatch(participante -> participante.getNombre().equals(nombreUsuario)) ||
                        grupo.getAdministrador().getNombre().equals(nombreUsuario))
                .map(Grupo::getNombre).toList();
    }
    @Override
    public Grupo obtenerGrupoPorNombre(String nombreGrupo) {
        saveGrupos(obtenerGrupos());
        return obtenerGrupos().stream()
                .filter(g -> g != null && g.getNombre() != null)
                .filter(g -> g.getNombre().equals(nombreGrupo))
                .findFirst()
                .orElse(null);
    }
    @Override
    public Grupo ingresar(Grupo grupo) {
        saveGrupos(obtenerGrupos());
        return obtenerGrupos().stream()
                .filter(g -> g != null && g.getNombre() != null && g.getPassword() != null)
                .filter(g -> g.getNombre().equals(grupo.getNombre()) && g.getPassword().equals(grupo.getPassword()) && g.isPublico() == grupo.isPublico())
                .findFirst()
                .orElse(null);
    }
    @Override
    public boolean addGroup(Grupo grupo) {
        obtenerGrupos().add(grupo);
        return saveGrupos(obtenerGrupos());
    }
    @Override
    public void agregarMiembroGrupo(Grupo grupo, Usuario miembro) {
        boolean exists = grupo.getParticipantes().stream()
                .anyMatch(p -> p.getNombre().equals(miembro.getNombre())
                        && p.getPassword().equals(miembro.getPassword()));
        if (!exists) {
            grupo.getParticipantes().add(miembro);
        }
    }
}