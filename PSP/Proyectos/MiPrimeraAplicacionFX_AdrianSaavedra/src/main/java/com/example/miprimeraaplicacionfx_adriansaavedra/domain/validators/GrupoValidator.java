package com.example.miprimeraaplicacionfx_adriansaavedra.domain.validators;

import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Grupo;

public class GrupoValidator {
    public boolean validateGrupo (Grupo grupo){
        if (grupo.getNombre().isBlank() ||grupo.getNombre().isEmpty()){
            return false;
        } else return !grupo.getPassword().isBlank() && !grupo.getPassword().isEmpty();
    }
}
