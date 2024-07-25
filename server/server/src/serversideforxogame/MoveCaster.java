/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversideforxogame;

import serversideforxogame.DTO.Move;

/**
 *
 * @author Administrator
 */
public class MoveCaster {
    private MoveCaster() {}

    public static Move castToMove(String move) {
        String[] moveTokens = move.split(",");
        return new Move(
                Integer.parseInt(moveTokens[0]),
                Integer.parseInt(moveTokens[1]),
                moveTokens[2].charAt(0)
        );
    }
}
