package org.example.webexamenadriansaavedra.dao;

import org.springframework.stereotype.Repository;

@Repository
public class DaoUsuario {
    private Database database;

    public DaoUsuario(Database database) {
        this.database = database;
    }
}
