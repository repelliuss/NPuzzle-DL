package com.repelliuss.npuzzle.game;

import com.repelliuss.npuzzle.utilities.Index2D;

public interface GameBoard {
    int getRow();
    int getColumn();
    BoardPiece getPiece(Index2D pos);
}
