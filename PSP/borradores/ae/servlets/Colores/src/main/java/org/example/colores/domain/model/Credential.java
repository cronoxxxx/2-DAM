package org.example.colores.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.colores.common.Constantes;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    private String username;
    private String password;
    private String color;

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
        this.color = Constantes.PRED_COLOR;
    }
}
