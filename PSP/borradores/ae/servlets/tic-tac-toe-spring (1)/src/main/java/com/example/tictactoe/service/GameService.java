package com.example.tictactoe.service;

import com.example.tictactoe.model.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {
    private final Random random = new Random();

    public Game startNewGame(String player1, String player2) {
        return new Game(player1, player2);
    }

    public void makeMove(Game game, int position) {
        if (game.getBoard()[position] == null && !game.isGameOver()) {
            game.getBoard()[position] = game.getCurrentPlayer();
            if (checkWin(game)) {
                game.setWinner(game.getCurrentPlayer());
                game.setGameOver(true);
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
}

