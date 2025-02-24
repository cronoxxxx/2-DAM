package org.example.restadriansaavedra.dao;

import org.example.restadriansaavedra.common.errors.MouseAlreadyExistsException;
import org.example.restadriansaavedra.domain.model.Mouse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MouseDao {
    private final Database database;

    public MouseDao(Database database) {
        this.database = database;
    }

    public List<Mouse> findAll() {
        return database.getMice();
    }

    public void save(Mouse mouse) {
        boolean b = database.getMice().stream().anyMatch(m -> m.getName().equals(mouse.getName()));
        if (b) {
            throw new MouseAlreadyExistsException("Mouse already exists: " + mouse.getName());
        }
        database.getMice().add(mouse);
    }
}