package org.example.restadriansaavedra.domain.validators;

import org.example.restadriansaavedra.common.errors.MouseValidatorException;
import org.example.restadriansaavedra.domain.model.Mouse;
import org.springframework.stereotype.Component;

@Component
public class RatonValidator {
    public boolean validate(Mouse raton) {
        if (raton.getName().isBlank() || raton.getAge() <= 0) {
            throw new MouseValidatorException("El raton debe tener un nombre y una edad mayor a 0");
        } else {
            return true;
        }
    }

}
