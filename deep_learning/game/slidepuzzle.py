#!/usr/bin/env python3

from abc import ABC, abstractmethod
from .move import Move
from .piece import Piece

class SlidePuzzle(ABC):

    def __init__(self):
        self.__last_move = Move.STAY
        self.__move_count = 0

    @property
    def last_move(self):
        return self.__last_move

    @last_move.setter
    def last_move(self, move):
        self.__last_move = move

    @property
    def move_count(self):
        return self.__move_count

    def __inc_move_count(self):
        ++self.__move_count

    def __reset_move_count(self):
        self.__move_count = 0

    @abstractmethod
    def get_pos_blank(self):
        pass

    @abstractmethod
    def __update_pos_blank(self, delta_y, delta_x):
        pass

    @property
    @abstractmethod
    def row(self):
        pass

    @row.setter
    @abstractmethod
    def row(self, row):
        pass

    @property
    @abstractmethod
    def column(self):
        pass

    @column.setter
    @abstractmethod
    def column(self, column):
        pass

    @abstractmethod
    def get_cell(self, pos):
        pass

    @abstractmethod
    def __set_cell(self, pos, other):
        pass

    def move(self, move):
        if(self.check_move(move)):
            y, x = self.get_pos_blank()
            delta_y, delta_x = 0, 0
            if move == Move.LEFT:
                x -= 1
                delta_x = -1
            elif move == Move.RIGHT:
                x += 1
                delta_x = 1
            elif move == Move.UP:
                y -= 1
                delta_y = -1
            elif move == Move.DOWN:
                y += 1
                delta_y = 1
            else:
                return False

            self.__inc_move_count()
            self.last_move = move
            self.__swap_cell(self.get_pos_blank(), (y, x))
            self.__update_pos_blank(delta_y, delta_x)
            return True
        else:
            return False

    
    def check_move(self, move):
        switcher = {
            Move.LEFT: (0, -1),
            Move.RIGHT: (0, 1),
            Move.UP: (-1, 0),
            Move.DOWN: (1, 0)
        }

        case = switcher.get(move, Move.STAY)
        if(case != Move.STAY):
            return self.check_delta_move(case[0], case[1])
        else:
            return False

    
    def check_delta_move(self, y_move, x_move):
        return (self.get_pos_blank()[0] + y_move < self.row and
                self.get_pos_blank()[0] + y_move >= 0 and
                self.get_pos_blank()[1] + x_move < self.column and
                self.get_pos_blank()[1] + x_move >= 0)

    def __swap_cell(self, left, right):
        temp = Piece(self.get_cell(left).id,
                                 self.get_cell(left).value)
        self.__set_cell(left, self.get_cell(right))
        self.__set_cell(right, temp)
