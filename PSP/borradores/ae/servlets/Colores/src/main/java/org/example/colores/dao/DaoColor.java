package org.example.colores.dao;

import lombok.RequiredArgsConstructor;
import org.example.colores.common.Constantes;
import org.example.colores.domain.model.Credential;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class DaoColor {
    private final Database database;

    public void changeColor(Credential credential) {
        database.getCredentials().stream()
                .filter(cred -> cred.getUsername().equals(credential.getUsername()))
                .findFirst()
                .ifPresent(cred -> cred.setColor(credential.getColor()));
    }

    public String getUserColor(String username) {
        return database.getCredentials().stream()
                .filter(cred -> cred.getUsername().equals(username))
                .findFirst()
                .map(Credential::getColor)
                .orElse(Constantes.PRED_COLOR);
    }

    public String getGlobalColor() {
        return database.getGlobalColor();
    }

    public void setGlobalColor(String color) {
        database.setGlobalColor(color);
    }
}
