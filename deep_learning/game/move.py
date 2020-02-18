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
            return 1
        elif move == Move.RIGHT:
            return 2
        elif move == Move.UP:
            return 3
        elif move == Move.DOWN:
            return 4
        else:
            return 0
