/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MoveDeserializer;

/**
 *
 * @author Haithum
 */
public class GameManager {
    private static final String[] xoTos = new String[]{"X", "O"};

    private GameManager() {}

    public static void startNewGame(DataInputStream reader, DataOutputStream writer) {
        Game game = new Game();
        try {
            writer.writeUTF(xoTos[(int)(Math.random() * (2))]);
        } catch (IOException exception) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, exception);
        }

        do {
            try {
                String move = reader.readUTF();
                game.makeMove(MoveDeserializer.deserialize(move));
                writer.writeUTF(game.getStatus());
            } catch (IOException exception) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, exception);
            }
        } while (game.isNotOver());
    }
}
