/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import game.Move;

/**
 *
 * @author Administrator
 */
public class MoveDeserializer {
    private MoveDeserializer() {}
    
    public static Move deserialize(String move) {
        String[] moveTokens = move.split(",");
        return new Move(
                Integer.parseInt(moveTokens[0]),
                Integer.parseInt(moveTokens[1]),
                moveTokens[2].charAt(0)
        );
    }
}
