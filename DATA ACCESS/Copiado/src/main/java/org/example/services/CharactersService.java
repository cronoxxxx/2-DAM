package org.example.services;

import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.example.dao.CharactersDao;
import org.example.model.error.GameError;

public class CharactersService {
    private final CharactersDao charactersDao;

    @Inject
    public CharactersService(CharactersDao charactersDao) {
        this.charactersDao = charactersDao;
    }

    public Either<GameError, Integer> delete(int id, boolean confirmed) {
        if (!confirmed) {
            return this.charactersDao.delete(id, confirmed);
        } else {
            this.charactersDao.save(id);
            return this.charactersDao.delete(id, confirmed);
        }
    }
}