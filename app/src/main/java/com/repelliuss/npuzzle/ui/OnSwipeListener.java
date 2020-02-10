package com.repelliuss.npuzzle.ui;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.repelliuss.npuzzle.utils.Move;

public abstract class OnSwipeListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float x1 = e1.getX();
        float y1 = e1.getY();

        float x2 = e2.getX();
        float y2 = e2.getY();

        Move direction = getDirection(x1, y1, x2, y2);
        return onSwipe(direction);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    protected abstract boolean onSwipe(Move direction);

    private Move getDirection(float x1, float y1, float x2, float y2) {
        double angle = getAngle(x1, y1, x2, y2);
        return Move.fromAngle(angle);
    }

    private double getAngle(float x1, float y1, float x2, float y2) {

        double rad = Math.atan2(y1 - y2, x2 - x1) + Math.PI;
        return (rad * 180/ Math.PI + 180) % 360;
    }
}
