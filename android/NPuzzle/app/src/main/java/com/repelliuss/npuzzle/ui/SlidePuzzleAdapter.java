package com.repelliuss.npuzzle.ui;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.repelliuss.npuzzle.GameEventHandler;
import com.repelliuss.npuzzle.R;
import com.repelliuss.npuzzle.game.GameStatus;
import com.repelliuss.npuzzle.game.SlidePuzzle;
import com.repelliuss.npuzzle.utils.Index2D;
import com.repelliuss.npuzzle.utils.Move;
import com.repelliuss.npuzzle.utils.Screen;

public final class SlidePuzzleAdapter<T>
        extends RecyclerView.Adapter<SlidePuzzleAdapter<T>.ViewHolder> {

    private final SlidePuzzle<T> puzzle;
    private LayoutInflater inflater;
    private Resources resources;
    private GameEventHandler handler;
    private TextView moveNum;
    private int pxHeight;
    private int pxWidth;
    private MediaPlayer sliding;

    public SlidePuzzleAdapter(Context context, final SlidePuzzle<T> argPuzzle,
                              final GameEventHandler argHandler, final TextView argMoveNum) {
        inflater = LayoutInflater.from(context);
        resources = context.getResources();
        puzzle = argPuzzle;
        handler = argHandler;
        moveNum = argMoveNum;

        sliding = MediaPlayer.create(context, R.raw.sliding);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        dpHeight /= 1.5f;
        pxHeight = Screen.dpToPixel(dpHeight, context.getResources());

        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        dpWidth -= 40;
        pxWidth = Screen.dpToPixel(dpWidth, context.getResources());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView number;

        ViewHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.txt_number);

            float viewHeight = pxHeight / puzzle.getRow();
            float viewWidth = pxWidth / puzzle.getColumn();
            float viewSize = viewHeight < viewWidth ? viewHeight : viewWidth;

            float maxHeight = pxHeight / 3;
            float maxWidth = pxWidth / 3;
            float maxSize = maxHeight < maxWidth ? maxHeight : maxWidth;

            ViewGroup.LayoutParams layoutParams = number.getLayoutParams();
            layoutParams.height = (int) viewSize;
            layoutParams.width = (int) viewSize;
            number.setLayoutParams(layoutParams);

            float textSize = viewSize * 24 / maxSize;
            textSize =  textSize < 14f ? 14f : textSize;
            number.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }

        TextView getTextView() {
            return number;
        }
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
                    ResourcesCompat.getColor(resources, R.color.colorWineDregs, null));
        }
        else {
            holder.getTextView().setText("");
            holder.getTextView().setBackgroundColor(
                    ResourcesCompat.getColor(resources, R.color.colorGainsboro, null));
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

        sliding.start();
        notifyItemMoved(oldPos, newPos);
        if(blankMove == Move.UP)
            notifyItemMoved(newPos + 1, oldPos);
        else if(blankMove == Move.DOWN)
            notifyItemMoved(newPos - 1, oldPos);

        moveNum.setText(String.valueOf(puzzle.getMoveCount()));
        if(puzzle.getStatus() == GameStatus.FINISHED) handler.onGameFinish();
    }
}
