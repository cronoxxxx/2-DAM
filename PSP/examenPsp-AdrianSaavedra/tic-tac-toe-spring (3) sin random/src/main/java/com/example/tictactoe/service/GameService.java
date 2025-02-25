package com.example.tictactoe.service;

import com.example.tictactoe.dao.GameDao;
import com.example.tictactoe.model.Game;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameDao gameDao;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public Game startNewGame(String player1, String player2) {
       return gameDao.startNewGame(player1,player2);
    }

    public void makeMove(Game game, int position) {
       gameDao.makeMove(game,position);
    }
}

