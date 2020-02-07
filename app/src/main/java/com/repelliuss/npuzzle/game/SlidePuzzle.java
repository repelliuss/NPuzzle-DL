package com.repelliuss.npuzzle.game;

import androidx.annotation.NonNull;

import com.repelliuss.npuzzle.utilities.Index2D;

public abstract class SlidePuzzle<T> implements Puzzle<SlidePuzzle<T>.Piece> {

    private Move lastMove;
    private int moveCount;

    enum Cell {
        VALUE,
        BLANK
    }

    public class Piece implements BoardPiece<Cell, T> {

        Cell id;
        T value;

        public Piece(Cell argID) {
            setId(argID);
        }

        public Piece(Cell argId, final T argValue) {
             setPiece(argId, argValue);
        }

        public Piece(final Piece other) {
            id = other.getId();
            value = other.getValue();
        }

        @Override
        public Cell getId() { return id; }

        @Override
        public T getValue() { return value; }

        public void setId(final Cell argId) { id = argId; }

        public void setValue(final T argValue) { value = argValue; }

        public void setPiece(final Piece other) {
            setId(other.getId());
            setValue(other.getValue());
        }

        public void setPiece(Cell argId, final T argValue) {
            setId(argId);
            setValue(argValue);
        }
    }

    protected SlidePuzzle() {

        lastMove = Move.STAY;
        moveCount = 0;
    }

    @Override
    public Move getLastMove() {
        return lastMove;
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    private void setLastMove(Move argLastMove) {
        lastMove = argLastMove;
    }

    private void incMoveCount() {
        ++moveCount;
    }

    protected abstract Index2D getPosBlank();
    protected abstract void setRow(int argColumn);
    protected abstract void setColumn(int argColumn);
    protected abstract void setPiece(Index2D pos, final Piece piece);

    @Override
    public boolean move(@NonNull final Move move) {
        if(checkMove(move)) {
            switch(move) {
                case LEFT:
                    swapCell(getPosBlank(), Index2D.getRelativeToX(getPosBlank(), -1));
                    getPosBlank().decX();
                    break;
                case RIGHT:
                    swapCell(getPosBlank(), Index2D.getRelativeToX(getPosBlank(), 1));
                    getPosBlank().incX();
                    break;
                case UP:
                    swapCell(getPosBlank(), Index2D.getRelativeToY(getPosBlank(), -1));
                    getPosBlank().decY();
                    break;
                case DOWN:
                    swapCell(getPosBlank(), Index2D.getRelativeToY(getPosBlank(), 1));
                    getPosBlank().incY();
                    break;
            }

            incMoveCount();
            setLastMove(move);

            return true;
        }

        return false;
    }

    @Override
    public boolean checkMove(@NonNull final Move move) {
        switch(move) {
            case LEFT:
                return checkMove(0, -1);
            case RIGHT:
                return checkMove(0, 1);
            case UP:
                return checkMove(-1, 0);
            case DOWN:
                return checkMove(1, 0);
            default:
                return false;
        }
    }

    @Override
    public boolean checkMove(int yMove, int xMove) {

        return  getPosBlank().getY() + yMove < getRow() &&
                getPosBlank().getY() + yMove >= 0 &&
                getPosBlank().getX() + xMove < getColumn() &&
                getPosBlank().getX() + xMove >= 0;
    }

    private void swapCell(Index2D left, Index2D right) {
        Piece temp = new Piece(getPiece(left));
        setPiece(left, getPiece(right));
        setPiece(right, temp);
    }
}
