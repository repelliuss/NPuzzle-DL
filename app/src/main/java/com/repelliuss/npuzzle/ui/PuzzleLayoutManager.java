package com.repelliuss.npuzzle.ui;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;

public class PuzzleLayoutManager extends GridLayoutManager {

    private boolean scrollable = false;

    public PuzzleLayoutManager(Context context, int columnCount) {
        super(context, columnCount);
    }

    @Override
    public boolean canScrollVertically() {
        return scrollable;
    }
}
