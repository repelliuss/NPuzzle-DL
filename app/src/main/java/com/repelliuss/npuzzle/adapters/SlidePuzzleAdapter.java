package com.repelliuss.npuzzle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.repelliuss.npuzzle.R;
import com.repelliuss.npuzzle.game.SlidePuzzle;
import com.repelliuss.npuzzle.utilities.Index2D;

public final class SlidePuzzleAdapter<T>
        extends RecyclerView.Adapter<SlidePuzzleAdapter<T>.ViewHolder> {

    private final SlidePuzzle<T> puzzle;
    private LayoutInflater inflater;

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

        if(piece.getId() == SlidePuzzle.Cell.VALUE)
            holder.getTextView().setText(String.valueOf(piece.getValue()));
        else holder.getTextView().setBackgroundColor(0xFF12FF45);
    }

    @Override
    public int getItemCount() {
        return puzzle.getRow() * puzzle.getColumn();
    }
}
