package com.repelliuss.npuzzle.utils;

public enum Move {

    STAY,
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public static Move fromAngle(double angle) {
        if(inRange(angle, 45, 135)) {
            return UP;
        }
        else if(inRange(angle, 0,45) || inRange(angle, 315, 360)) {
            return RIGHT;
        }
        else if(inRange(angle, 225, 315)) {
            return DOWN;
        }
        else {
            return LEFT;
        }

    }

    private static boolean inRange(double angle, float init, float end){
        return (angle >= init) && (angle < end);
    }

    public static void swap(Move[] arr, int left, int right) {
        Move temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }


    public static Move toMove(int direction) {

        switch(direction) {
            case 0:
                return LEFT;
            case 1:
                return RIGHT;
            case 2:
                return UP;
            case 3:
                return DOWN;
        }

        return STAY;
    }

    public static Move toOpposite(Move move) {

        switch(move) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
        }

        return null;
    }
}
