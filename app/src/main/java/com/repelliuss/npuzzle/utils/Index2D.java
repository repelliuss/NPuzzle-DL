package com.repelliuss.npuzzle.utils;

public class Index2D {
    private int y;
    private int x;

    public Index2D() {
        y = 0;
        x = 0;
    }

    public Index2D(int argY, int argX) {
        y = argY;
        x = argX;
    }

    public Index2D(final Index2D other) {
        y = other.getY();
        x = other.getX();
    }

    public int getY() {
        return y;
    }

    public int getX() { return x; }

    public void setY(int argY) { y = argY; }

    public void setX(int argX) { x = argX; }

    public void setTo(int argY, int argX) {
        setY(argY);
        setX(argX);
    }

    public void setTo(final Index2D other) {
        setY(other.getY());
        setX(other.getX());
    }

    public void incX() {
        ++x;
    }

    public void incY() {
        ++y;
    }

    public void decX() {
        --x;
    }

    public void decY() {
        --y;
    }

    public int toLinear(int width) {
        return width * getY() + getX();
    }

    public static Index2D getRelativeToY(final Index2D index, int deltaY) {
        return getRelativeTo(index, deltaY, 0);
    }

    public static Index2D getRelativeToX(final Index2D index, int deltaX) {
        return getRelativeTo(index, 0, deltaX);
    }

    public static Index2D getRelativeTo(final Index2D index, int deltaY, int deltaX) {
        return new Index2D(index.getY() + deltaY, index.getX() + deltaX);
    }
}
