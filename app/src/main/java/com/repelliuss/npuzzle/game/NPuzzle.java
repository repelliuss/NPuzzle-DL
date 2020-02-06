package com.repelliuss.npuzzle.game;

import com.repelliuss.npuzzle.utilities.Index2D;

public final class NPuzzle extends SlidePuzzle<int> {

    private Piece[] board;
    private int row, column;

    @Override
    public int getRow() { return row; }

    @Override
    public int getColumn() { return column; }

    @Override
    public Piece getPiece(Index2D pos) {
        return board[getIndex(pos)];
    }

    @Override
    protected void setRow(int argRow) { row = argRow; }

    @Override
    protected void setColumn(int argColumn) { column = argColumn; }

    @Override
    protected void setPiece(Index2D pos, final Piece piece) {
        board[getIndex(pos)].setPiece(piece);
    }

    @Override
    public void reset() {
        int number = 1;

        for(int i = 0; i < getRow(); ++i) {
            for(int j = 0; j < getColumn() - 1; ++j) {
                getPiece(new Index2D(i, j)).setPiece(Cell.VALUE, number++);
            }
            number += 2;
        }

        number = getColumn();

        for(int i = 0; i < getRow() - 1; ++i) {
            getPiece(new Index2D(i, getColumn() - 1)).setPiece(Cell.VALUE, number);
            number += getColumn();
        }

        getPiece(new Index2D(getRow() - 1, getColumn() - 1)).setId(Cell.BLANK);
    }

    private int getIndex(final Index2D index2D) {
        return getColumn() * index2D.getY() + index2D.getX();
    }
}
