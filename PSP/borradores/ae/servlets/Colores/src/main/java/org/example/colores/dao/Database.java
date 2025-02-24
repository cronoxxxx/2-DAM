package org.example.colores.dao;

import lombok.Data;
import lombok.Getter;
import org.example.colores.common.Constantes;
import org.example.colores.domain.model.Credential;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Database {
    private final List<Credential> credentials;
    private String globalColor;

    public Database() {
        credentials = new ArrayList<>();
        globalColor = Constantes.PRED_COLOR;
    }
}
