package com.repelliuss.npuzzle.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PuzzlePieceDecoration extends RecyclerView.ItemDecoration {

    private final int columnCount;
    private final int spacing;

    public PuzzlePieceDecoration(int argColumnCount, int argSpacing) {
        columnCount = argColumnCount;
        spacing = argSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NonNull View view, RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % columnCount;

        outRect.left = column * spacing / columnCount;
        outRect.right = spacing - (column + 1) * spacing / columnCount;
    }
}
