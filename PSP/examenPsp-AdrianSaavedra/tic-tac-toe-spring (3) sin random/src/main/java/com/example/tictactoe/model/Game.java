package com.example.tictactoe.model;

import lombok.Data;

@Data
public class Game {
    private String[] board;
    private String currentPlayer;
    private String player1;
    private String player2;
    private String winner;
    private boolean gameOver;

    public Game(String player1, String player2) {
        this.board = new String[9];
        this.currentPlayer = "X";
        this.player1 = player1;
        this.player2 = player2;
        this.winner = null;
        this.gameOver = false;
    }
}

