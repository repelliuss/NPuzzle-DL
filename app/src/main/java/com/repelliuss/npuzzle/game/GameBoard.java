package com.repelliuss.npuzzle.game;

import com.repelliuss.npuzzle.utilities.Index2D;

public interface GameBoard<E extends BoardPiece> {
    int getRow();
    int getColumn();
    E getPiece(Index2D pos);
}