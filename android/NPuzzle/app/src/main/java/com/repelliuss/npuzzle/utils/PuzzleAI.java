package com.repelliuss.npuzzle.utils;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;

import androidx.appcompat.app.AppCompatActivity;

import com.repelliuss.npuzzle.game.BoardPiece;
import com.repelliuss.npuzzle.game.Puzzle;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class PuzzleAI<E extends BoardPiece> implements AI<Move> {

    private Interpreter tflite;
    private String row;
    private String column;
    private AppCompatActivity activity;
    private Puzzle<E> puzzle;
    private Move aiLastMove;

    public PuzzleAI(final AppCompatActivity argActivity, final Puzzle<E> argPuzzle) {
        puzzle = argPuzzle;
        row = String.valueOf(puzzle.getRow());
        column = String.valueOf(puzzle.getColumn());
        activity = argActivity;
        aiLastMove = Move.STAY;

        loadModel();
    }

    /**
     * This method gives input board to appropriate model and
     * takes its predictions. First it takes prediction with
     * highest value, then it looks if the prediction it
     * made is a valid move or does it get the board to the 1
     * move before position. If it does, then it takes next
     * highest prediction until a valid one.
     * @return valid, appropriate prediction with highest value
     */
    @Override
    public Move predict() {

        float[][] predictions = doInference(puzzle.getInputBoard());
        int direction = 0;
        Move move;
        boolean[] validMoves = {true, true, true, true};

        do {
            float max = 0.0f;
            for (int i = 0; i < 4; ++i) {
                if (predictions[0][i] > max) {
                    if(validMoves[i]) {
                        max = predictions[0][i];
                        direction = i;
                    }
                }
            }

            validMoves[direction] = false;
            move = Move.toMove(direction);
        }while(move == Move.toOpposite(aiLastMove) || !puzzle.checkMove(move));

        aiLastMove = move;

        return move;
    }

    private float[][] doInference(float[][] inputBoard) {

        float[][] outputVal = new float[1][4];
        tflite.run(inputBoard, outputVal);

        return outputVal;
    }

    private void loadModel() {

        String modelFile = row + 'x' + column + ".tflite";
        try {
            tflite= new Interpreter(loadModelFile(activity, modelFile));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MappedByteBuffer loadModelFile(Activity activity, String MODEL_FILE)
            throws IOException {

        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_FILE);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
