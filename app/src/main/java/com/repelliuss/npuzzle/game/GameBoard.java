package com.repelliuss.npuzzle.game;

public interface GameBoard<T> {

    int getRow();
    int getColumn();
    T getCell();
    void setRow(int argColumn);
    void setColumn(int argColumn);
    void setCell(int yPos, int xPos, T value);
}
