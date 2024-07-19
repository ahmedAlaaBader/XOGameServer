/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Administrator
 */
public class Game {
    private final Character[][] gameBoard;
    private String status;
    private int filledPieces = 0;
    
    public Game() {
        gameBoard = new Character[3][3];
    }
    
    public void makeMove(Move move) {
        System.out.println("Make Move: " + move);
        gameBoard[move.getRow()][move.getColumn()] = move.getXo();
        filledPieces++;
        
        this.status = computeStatus();
    }
    
    private String computeStatus() {
        if (filledPieces == 9) {
            return "Draw";
        }

        Character winner = null;
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0] != null
                    && gameBoard[i][0].equals(gameBoard[i][1])
                    && gameBoard[i][0].equals(gameBoard[i][2])) {
                winner = gameBoard[i][0];
            }
        }

        for (int i = 0; i < 3; i++) {
            if (gameBoard[0][i] != null
                    && gameBoard[0][i].equals(gameBoard[1][i])
                    && gameBoard[0][i].equals(gameBoard[2][i])) {
                winner = gameBoard[0][i];
            }
        }

        if (gameBoard[0][0] != null
                && gameBoard[0][0].equals(gameBoard[1][1])
                && gameBoard[0][0].equals(gameBoard[2][2])) {
            winner = gameBoard[0][0];
        }
        if (gameBoard[0][2] != null
                && gameBoard[0][2].equals(gameBoard[1][1])
                && gameBoard[0][2].equals(gameBoard[2][0])) {
            winner = gameBoard[0][2];
        }

        return winner == null ? "continue" : ("Winner " + winner);
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isNotOver() {
        return "continue".equals(this.status);
    }
}