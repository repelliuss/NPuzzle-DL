package com.repelliuss.npuzzle.game;

import androidx.annotation.NonNull;

import com.repelliuss.npuzzle.utilities.Move;

public interface Puzzle<E extends BoardPiece> extends GameBoard<E> {

    Move getLastMove();
    int getMoveCount();
    boolean move(@NonNull final Move move);
    boolean checkMove(@NonNull final Move move);
    boolean checkMove(int yMove, int xMove);
    void reset();
    boolean isSolved();

}