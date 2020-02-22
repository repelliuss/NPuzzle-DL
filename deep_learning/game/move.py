#!/usr/bin/env python3

from enum import Enum

class Move(Enum):
    STAY = 0
    LEFT = 1
    RIGHT = 2
    UP = 3
    DOWN = 4

    def to_int(move):
        if move == Move.LEFT:
            return 0
        elif move == Move.RIGHT:
            return 1
        elif move == Move.UP:
            return 2
        elif move == Move.DOWN:
            return 3
        else:
            return 0

    def opposite(move):
        if move == Move.LEFT:
            return Move.RIGHT
        elif move == Move.RIGHT:
            return Move.LEFT
        elif move == Move.UP:
            return Move.DOWN
        elif move == Move.DOWN:
            return Move.UP
        else:
            return None
