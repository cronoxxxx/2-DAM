package org.example.restadriansaavedra.domain.service;

import org.example.restadriansaavedra.dao.MouseDao;
import org.example.restadriansaavedra.domain.model.Mouse;
import org.example.restadriansaavedra.domain.validators.RatonValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MouseService {
    private final MouseDao mouseDao;
    private final RatonValidator ratonValidator;

    public MouseService(MouseDao mouseDao, RatonValidator ratonValidator) {
        this.mouseDao = mouseDao;
        this.ratonValidator = ratonValidator;
    }

    public List<Mouse> getAllMice() {
        return mouseDao.findAll();
    }

    public void addMouse(Mouse mouse) {
        if (!ratonValidator.validate(mouse)) return;
        mouseDao.save(mouse);
    }
}