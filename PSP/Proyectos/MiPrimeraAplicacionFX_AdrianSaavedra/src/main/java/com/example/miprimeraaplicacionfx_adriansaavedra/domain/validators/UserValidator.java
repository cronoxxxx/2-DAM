package com.example.miprimeraaplicacionfx_adriansaavedra.domain.validators;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Usuario;

public class UserValidator {

    public boolean validateUser(Usuario user) {

        if (user.getNombre().isBlank() || user.getNombre().isEmpty()) {
            return false;
        } else return !user.getPassword().isBlank() && !user.getPassword().isEmpty();
    }
}
