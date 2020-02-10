package com.repelliuss.npuzzle.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.repelliuss.npuzzle.R;
import com.repelliuss.npuzzle.game.SlidePuzzle;
import com.repelliuss.npuzzle.utils.Index2D;
import com.repelliuss.npuzzle.utils.Move;

public final class SlidePuzzleAdapter<T>
        extends RecyclerView.Adapter<SlidePuzzleAdapter<T>.ViewHolder> {

    private final SlidePuzzle<T> puzzle;
    private LayoutInflater inflater;
    private Resources resources;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView number;

        ViewHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.txt_number);
        }

        TextView getTextView() {
            return number;
        }
    }

    public SlidePuzzleAdapter(Context context, final SlidePuzzle<T> argPuzzle) {
        inflater = LayoutInflater.from(context);
        resources = context.getResources();
        puzzle = argPuzzle;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.number_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Index2D index = new Index2D(position / puzzle.getColumn(),
                                    position % puzzle.getColumn());
        SlidePuzzle<T>.Piece piece = puzzle.getPiece(index);

        if(piece.getId() == SlidePuzzle.Cell.VALUE) {
            holder.getTextView().setText(String.valueOf(piece.getValue()));
            holder.getTextView().setBackgroundColor(
                    ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null));
        }
        else {
            holder.getTextView().setText("");
            holder.getTextView().setBackgroundColor(
                    ResourcesCompat.getColor(resources, R.color.colorYellow, null));
        }
    }

    @Override
    public int getItemCount() {
        return puzzle.getRow() * puzzle.getColumn();
    }

    public void notifyBlankMoved(@NonNull Move blankMove) {

        int newPos = puzzle.getPosBlank().toLinear(puzzle.getColumn());
        int oldPos = newPos;

        switch(blankMove) {
            case UP:
                oldPos += puzzle.getColumn();
                break;

            case DOWN:
                oldPos -= puzzle.getColumn();
                break;

            case LEFT:
                oldPos += 1;
                break;

            case RIGHT:
                oldPos -= 1;
                break;
        }

        notifyItemMoved(oldPos, newPos);
        if(blankMove == Move.UP)
            notifyItemMoved(newPos + 1, oldPos);
        else if(blankMove == Move.DOWN)
            notifyItemMoved(newPos - 1, oldPos);
    }
}
