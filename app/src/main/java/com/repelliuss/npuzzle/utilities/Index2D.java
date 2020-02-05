package com.repelliuss.npuzzle.utilities;

public final class Index2D {
    private int y;
    private int x;

    public Index2D(int argY, int argX) {
        y = argY;
        x = argX;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
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
}
