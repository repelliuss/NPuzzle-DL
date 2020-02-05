package com.repelliuss.npuzzle.game;

import com.repelliuss.npuzzle.utilities.Index2D;

public interface GameBoard<T> {

    int getRow();
    int getColumn();
    T getCell(Index2D position);
    void setRow(int argColumn);
    void setColumn(int argColumn);
    void setCell(Index2D position, T value);
}
