package org.example.loginspring_adriansaavedra.domain.validators;

import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerValidator {
    public boolean validatePlayer(PlayerEntity playerEntity) {
        return !playerEntity.getName().isBlank() && !playerEntity.getCountry().isBlank();
    }
}