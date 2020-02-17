#!/usr/bin/env python3

import game.npuzzle as npuzzle
import argparse

SAVE_PATH = "./"
FILE_NAME = "data_npuzzle"

parser = argparse.ArgumentParser()
parser.add_argument('-n', '--num', type=int, default=10, help='number of boards for each')
parser.add_argument('-p', '--path', type=str, default=SAVE_PATH, help='path where you want to save')
parser.add_argument('-r', '--row', nargs='*', type=int, default=[3], help='board row/rows')
parser.add_argument('-c', '--col', nargs='*', type=int, default=[3], help='board column/columns')
parser.add_argument('--name', type=str, default=FILE_NAME, help='file name to save')

args = parser.parse_args()
if len(args.row) != len(args.col):
    print('Number of board rows and columns are not equal!')
    raise SystemExit(1)

def generate_boards(num, row, column):
    pass

def optimize_data(data):
    pass

def balance_data(data):
    pass

def generate_data(num, rows, columns, shuffle=True):
    pass

def save_data(data, path):
    pass
