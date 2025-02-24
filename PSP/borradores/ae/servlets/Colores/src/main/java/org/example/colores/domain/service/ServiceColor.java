package org.example.colores.domain.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.colores.common.Constantes;
import org.example.colores.dao.DaoColor;
import org.example.colores.domain.model.Credential;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class ServiceColor {
    private final DaoColor daoColor;

    public void changeUserColor(Credential credential) {
        daoColor.changeColor(credential);
    }

    public String getUserColor(String username) {
        return daoColor.getUserColor(username);
    }

    public String getGlobalColor() {
        return daoColor.getGlobalColor();
    }

    public void setGlobalColor(String color) {
        daoColor.setGlobalColor(color);
    }
}

