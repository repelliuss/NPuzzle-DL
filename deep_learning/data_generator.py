#!/usr/bin/env python3

from game.npuzzle import NPuzzle
from game.move import Move
import argparse
import os
import numpy as np
import random

FILE_PATH = "./"
FILE_NAME = "data_npuzzle"

parser = argparse.ArgumentParser()
parser.add_argument('-snum', '--scenarios', type=int, default=10, help='number of game scenarios for each')
parser.add_argument('-p', '--path', type=str, default=FILE_PATH, help='path where you want to save')
parser.add_argument('-r', '--row', nargs='*', type=int, default=[3], help='board row/rows')
parser.add_argument('-c', '--col', nargs='*', type=int, default=[3], help='board column/columns')
parser.add_argument('--name', type=str, default=FILE_NAME, help='file name to save')
parser.add_argument('--cmplx', type=int, default=200, help='max number of random moves')

args = parser.parse_args()
if len(args.row) != len(args.col):
    print('Number of board rows and columns are not equal!')
    raise SystemExit(1)

FILE_PATH = args.path
FILE_NAME = args.name

def generate_boards(snum, row, column, cmplx):
    data = []

    #Move.RIGHT is not valid in solved puzzle state
    moves = [ Move.LEFT, Move.RIGHT, Move.UP, Move.DOWN ]
    for i in range(snum):

        puzzle = NPuzzle(row, column)
        index = np.random.randint(low=0, high=len(moves))
        domain = len(moves) - 1
        for j in range(cmplx):

            while not puzzle.move(moves[index]):
                invalid_move = moves.pop(index)
                moves.append(invalid_move)
                domain -= 1
                index = np.random.randint(low=0, high=domain)

            data.append([puzzle.numpy(), Move.to_int(Move.opposite(puzzle.last_move)), puzzle.move_count])

            moves.remove(Move.opposite(puzzle.last_move))
            moves.append(Move.opposite(puzzle.last_move))
            domain = len(moves) - 1
            index = np.random.randint(low=0, high=domain)

    return data

def optimize_data(data):
    for i in range(len(data)):
        current = sorted(data[i], key= lambda x: (x[0].tolist(), x[1], x[2]))
        j = 0
        while j < len(current) - 1:
            if np.array_equal(current[j][0], current[j+1][0]):
                del current[j+1]
            else:
                del current[j][2]
                j += 1
        del current[j][2]
        data[i] = current

    return data

def balance_data(data):
    min_left, min_right = len(data[0]), len(data[0])
    min_up, min_down = len(data[0]), len(data[0])
    for i in range(len(data)):
        current_left, current_right = 0, 0
        current_up, current_down = 0, 0
        for j in range(len(data[i])):
            if data[i][j][1] == 1:
                current_left += 1
            elif data[i][j][1] == 2:
                current_right += 1
            elif data[i][j][1] == 3:
                current_up += 1
            else:
                current_down += 1

        if current_left < min_left:
            min_left = current_left

        if current_right < min_right:
            min_right = current_right

        if current_down < min_down:
            min_down = current_down

        if current_up < min_up:
            min_up = current_up

        data[i][0].append((current_left, current_right, current_up))

    min_move = min(min_left, min_right, min_up, min_down)

    for i in range(len(data)):
        total_left = data[i][0][2][0]
        total_right = data[i][0][2][1]
        total_up = data[i][0][2][2]
        del data[i][0][2]

        second_per = total_left
        third_per = second_per + total_right
        fourth_per = third_per + total_up

        data[i] = sorted(data[i], key= lambda x: x[1])

        balanced = data[i][0:min_move]
        balanced.extend(data[i][second_per:second_per + min_move])
        balanced.extend(data[i][third_per:third_per + min_move])
        balanced.extend(data[i][fourth_per:fourth_per + min_move])
        data[i] = balanced

    return data

def generate_data(snum, rows, columns, cmplx, shuffle=True):
    data = []
    for i in range(len(rows)):
        ith_data = generate_boards(snum, rows[i], columns[i], cmplx)
        data.append(ith_data)

    data = optimize_data(data)
    data = balance_data(data)
    if shuffle:
        for i in range(len(data)):
            random.shuffle(data[i])

    return np.array(data)

    
def save_data(x_data, y_data):
    path = os.path.join(FILE_PATH, FILE_NAME)
    np.savez_compressed(path,
                        x_train=x_data[0,:,0],
                        x_test=x_data[0,:,1],
                        y_train=y_data[0,:,0],
                        y_test=y_data[0,:,1])
    print(f'Saved to {path}')
    print(f'Train length={len(x_data[0,:,0])}')
    print(f'Test length={len(y_data[0,:,0])}')

train_data = generate_data(args.scenarios, args.row, args.col, args.cmplx)
test_data = generate_data(args.scenarios // 6, args.row, args.col, args.cmplx)
save_data(train_data, test_data)
