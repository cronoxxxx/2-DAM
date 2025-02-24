package com.example.tictactoe.service;

import com.example.tictactoe.model.Game;
import org.springframework.stereotype.Service;

@Service
public class GameService {

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

