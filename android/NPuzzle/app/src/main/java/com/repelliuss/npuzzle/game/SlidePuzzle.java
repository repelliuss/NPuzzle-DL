package com.repelliuss.npuzzle.game;

import androidx.annotation.NonNull;

import com.repelliuss.npuzzle.utils.Index2D;
import com.repelliuss.npuzzle.utils.Move;

import java.util.Random;

public abstract class SlidePuzzle<T> implements Puzzle<SlidePuzzle<T>.Piece> {

    private Move lastMove;
    private int moveCount;
    private GameStatus status;
    private final int MIN_RANDOM_MOVE = 200;
    private final int EXTRA_RANDOM_MOVE = 200;

    public enum Cell {
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
        status = GameStatus.ONGOING;
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

    private void resetMoveCount() {
        moveCount = 0;
    }

    public abstract Index2D getPosBlank();
    protected abstract void setRow(int argRow);
    protected abstract void setColumn(int argColumn);
    protected abstract void setPiece(Index2D pos, final Piece piece);

    @Override
    public GameStatus getStatus() {
        return status;
    }

    private void setStatus(GameStatus updatedStatus) {
        status = updatedStatus;
    }

    @Override
    public void initialize() {
        randomize();
    }

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
            if(isSolved())
                setStatus(GameStatus.FINISHED);
            else setStatus(GameStatus.ONGOING);

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

    public void randomize() {

        Move[] moveList = new Move[]{ Move.UP, Move.DOWN, Move.LEFT, Move.RIGHT};
        Random random = new Random();
        int moveCount = MIN_RANDOM_MOVE + random.nextInt(EXTRA_RANDOM_MOVE);
        int range = moveList.length;
        int moveIndex = random.nextInt(range--);

        for(int i = 0; i < moveCount; ++i) {

            while(!move(moveList[moveIndex])) {
                Move.swap(moveList, moveIndex, range--);
                moveIndex = random.nextInt(range);
            }

            Move opposite = Move.toOpposite(moveList[moveIndex]);
            int oppositeIndex = 0;
            for(int j = 0; j < moveList.length; ++j)
                if(moveList[j] == opposite)
                    oppositeIndex = j;

            Move.swap(moveList, oppositeIndex, moveList.length - 1);
            range = moveList.length - 1;
            moveIndex = random.nextInt(range);
        }

        if(isSolved()) move(Move.LEFT);

        setLastMove(Move.STAY);
        resetMoveCount();
        setStatus(GameStatus.ONGOING);
    }

    private void swapCell(Index2D left, Index2D right) {
        Piece temp = new Piece(getPiece(left));
        setPiece(left, getPiece(right));
        setPiece(right, temp);
    }
}
