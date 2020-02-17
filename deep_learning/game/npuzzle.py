#!/usr/bin/env python3

from .slidepuzzle import SlidePuzzle
from .piece import Piece
from .cell import Cell
from .move import Move

class NPuzzle(SlidePuzzle):

    def __init__(self, row, column):
        super().__init__()
        self.__row = row
        self.__column = column
        self.__board = [[Piece(Cell.VALUE, y*column + x + 1) for x in range(column)]
                        for y in range(row)]
        self.__board[-1][-1] = Piece(Cell.BLANK, 0)
        self.__pos_blank = (row-1, column-1)

    def p(self):
        for y in range(self.__row):
            for x in range(self.__column):
                print(self.__board[y][x].value)

    @property
    def row(self):
        return self.__row

    @row.setter
    def _SlidePuzzle__row(self, row):
        self.__row = row

    @property
    def column(self):
        return self.__column

    @column.setter
    def _SlidePuzzle__column(self, column):
        self.__column = column

    def get_pos_blank(self):
        return self.__pos_blank

    def _SlidePuzzle__update_pos_blank(self, delta_y, delta_x):
        self.__pos_blank = (self.__pos_blank[0] + delta_y,
                            self.__pos_blank[1] + delta_x)

    def get_cell(self, pos):
        return self.__board[pos[0]][pos[1]]

    def _SlidePuzzle__set_cell(self, pos, other):
        self.get_cell(pos).copy_piece(other)

    def reset(self):
        number = 1
        for y in range(self.row):
            for x in range(self.column):
                self.get_cell((y,x)).set_piece(Cell.VALUE, number)
                number += 1

        self.get_cell((self.row - 1, self.column - 1)).set_piece(Cell.BLANK, 0)
        self.__pos_blank = (self.row - 1, self.column - 1)


    def is_solved(self):
        if self.get_cell(self.get_pos_blank()).id != Cell.BLANK:
            return False

        number = 1
        for y in range(self.row):
            for x in range(self.column - 1):
                if self.get_cell((y,x)).value != number:
                    return False
                number += 1
            number += 1

        number = self.column
        for y in range(self.row - 1):
            if self.get_cell((y, self.column - 1)).value != number:
                return False
            number += self.column

        return True
