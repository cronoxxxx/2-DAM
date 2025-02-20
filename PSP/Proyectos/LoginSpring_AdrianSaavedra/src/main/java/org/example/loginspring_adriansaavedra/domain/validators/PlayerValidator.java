package org.example.loginspring_adriansaavedra.domain.validators;

import org.example.loginspring_adriansaavedra.common.Constantes;
import org.example.loginspring_adriansaavedra.common.errors.PlayerValidatorException;
import org.example.loginspring_adriansaavedra.domain.model.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerValidator {
    public boolean validatePlayer(PlayerEntity playerEntity) {
        if(playerEntity.getName().isBlank() || playerEntity.getCountry().isBlank()){
            throw new PlayerValidatorException(Constantes.AT_LEAST_NAME_AND_COUNTRY_MUST_BE_FILLED);
        } else {
            return true;
        }

    }
}