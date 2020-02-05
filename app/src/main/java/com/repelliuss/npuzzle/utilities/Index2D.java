package com.repelliuss.npuzzle.utilities;

public final class Index2D {
    private final int yPos;
    private final int xPos;

    public Index2D(int argYPos, int argXPos) {
        yPos = argYPos;
        xPos = argXPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getXPos() {
        return xPos;
    }
}
