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
public class Move {
    private int row;
    private int column;
    private char xo;

    public Move() {}
    
    public Move(int row, int column, char xo) {
        this.row = row;
        this.column = column;
        this.xo = xo;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the xo
     */
    public char getXo() {
        return xo;
    }

    /**
     * @param xo the xo to set
     */
    public void setXo(char xo) {
        this.xo = xo;
    }

    @Override
    public String toString() {
        return "Move(row: " + getRow() + ", column: " + getColumn() + ", X or O: " + getXo() +")";
    }
    
}