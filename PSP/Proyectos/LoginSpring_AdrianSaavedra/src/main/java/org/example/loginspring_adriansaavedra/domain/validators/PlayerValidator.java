package org.example.loginspring_adriansaavedra.domain.validators;

import org.example.loginspring_adriansaavedra.common.errors.PlayerValidatorException;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerValidator {
    public boolean validatePlayer(PlayerEntity playerEntity) {
        if(playerEntity.getName().isBlank() || playerEntity.getCountry().isBlank()){
            throw new PlayerValidatorException("El nombre y el país son obligatorios");
        } else {
            return true;
        }

    }
}