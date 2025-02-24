package com.example.tictactoe.service;

import com.example.tictactoe.dao.UserDao;
import com.example.tictactoe.model.Game;
import com.example.tictactoe.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {
    private final UserDao userDao;
    private final Random random = new Random();

    public GameService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean registerUser(String username, String password) {
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        userDao.save(new User(username, password, 0));
        return true;
    }

    public User authenticateUser(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public Game startNewGame(String player1, String player2) {
        return new Game(player1, player2);
    }

    public void makeMove(Game game, int position) {
        if (game.getBoard()[position] == null && !game.isGameOver()) {
            game.getBoard()[position] = game.getCurrentPlayer();
            if (checkWin(game)) {
                game.setWinner(game.getCurrentPlayer());
                game.setGameOver(true);
                updateScores(game);
            } else if (isBoardFull(game)) {
                game.setGameOver(true);
            } else {
                game.setCurrentPlayer(game.getCurrentPlayer().equals("X") ? "O" : "X");
            }
        }
    }

    public boolean makeRandomMove(Game game) {
        if (game.isGameOver()) {
            return false;
        }

        List<Integer> availablePositions = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (game.getBoard()[i] == null) {
                availablePositions.add(i);
            }
        }

        if (availablePositions.isEmpty()) {
            return false;
        }

        int randomPosition = availablePositions.get(random.nextInt(availablePositions.size()));
        makeMove(game, randomPosition);
        return true;
    }

    private boolean checkWin(Game game) {
        String[] board = game.getBoard();
        int[][] winningCombinations = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},  // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},  // Columns
            {0, 4, 8}, {2, 4, 6}              // Diagonals
        };

        for (int[] combination : winningCombinations) {
            if (board[combination[0]] != null &&
                board[combination[0]].equals(board[combination[1]]) &&
                board[combination[0]].equals(board[combination[2]])) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull(Game game) {
        for (String cell : game.getBoard()) {
            if (cell == null) {
                return false;
            }
        }
        return true;
    }

    private void updateScores(Game game) {
        String winnerUsername = game.getWinner().equals("X") ? game.getPlayer1() : game.getPlayer2();
        String loserUsername = game.getWinner().equals("X") ? game.getPlayer2() : game.getPlayer1();

        User winner = userDao.findByUsername(winnerUsername);
        User loser = userDao.findByUsername(loserUsername);

        if (winner != null) {
            userDao.updateScore(winnerUsername, winner.getScore() + 1);
        }
        if (loser != null) {
            userDao.updateScore(loserUsername, Math.max(0, loser.getScore() - 1));
        }
    }
}

