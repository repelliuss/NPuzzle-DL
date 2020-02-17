#!/usr/bin/env python3

class Piece:

    def __init__(self, id, value):
        self.__id = id
        self.__value = value

    @property
    def id(self):
        return self.__id

    @id.setter
    def id(self, id):
        self.__id = id

    @property
    def value(self):
        return self.__value

    @value.setter
    def value(self, value):
        self.__value = value

    def copy_piece(self, other):
        self.__value = other.__value
        self.__id = other.__id

    def set_piece(self, id, value):
        self.__value = value
        self.__id = id
