/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversideforxogame.DTO;

import serversideforxogame.MoveCaster;

import java.util.Random;

/**
 * @author Haithum
 */
public class GameManager {

    private static final String[] xo = new String[]{"X", "O"};

    private GameManager() {
    }

    public static void startNewGame(OnlinePlayer playerOne, OnlinePlayer playerTwo) {
        Game game = new Game();

        int xoTos = (int) (Math.random() * (2)); // 0 or 1
        GamePlayer gamePlayerOne = new GamePlayer(playerOne, xo[xoTos], new Random().nextBoolean()); // true or false
        GamePlayer gamePlayerTwo = new GamePlayer(playerTwo, xo[1 - xoTos], !gamePlayerOne.isFirst());

        gamePlayerOne.informPlayer();
        gamePlayerTwo.informPlayer();

        GamePlayer currentPlayer = gamePlayerOne.isFirst() ? gamePlayerOne : gamePlayerTwo;
        do {
            String moveValue = currentPlayer.readMove(); //row,column,xoro
            Move move = MoveCaster.castToMove(moveValue);
            game.applyMove(move);

            gamePlayerOne.informStatus(game.getStatus());
            gamePlayerTwo.informStatus(game.getStatus());

            currentPlayer = (currentPlayer == gamePlayerOne) ? gamePlayerTwo : gamePlayerOne;
            currentPlayer.informMove(moveValue);
        } while (game.isNotOver());
    }

    public static class GamePlayer {

        private final OnlinePlayer player;
        private final String xo;
        private final Boolean first;

        public GamePlayer(OnlinePlayer player, String xo, boolean first) {
            this.player = player;
            this.xo = xo;
            this.first = first;
        }

        public void informPlayer() {
            Connector connector = player.connect();
            connector.sendBoolean(first);
            connector.sendMessage(xo);
        }

        public boolean isFirst() {
            return this.first;
        }

        public String readMove() {
            return player.connect().readMessage();
        }

        public void informMove(String moveValue) {
            player.connect().sendMessage(moveValue);
        }

        public void informStatus(String status) {
            player.connect().sendMessage(status);
        }
    }
}
