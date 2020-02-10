package com.repelliuss.npuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.repelliuss.npuzzle.game.Puzzle;
import com.repelliuss.npuzzle.game.SlidePuzzle;
import com.repelliuss.npuzzle.ui.OnSwipeListener;
import com.repelliuss.npuzzle.ui.PuzzlePieceDecoration;
import com.repelliuss.npuzzle.ui.SlidePuzzleAdapter;
import com.repelliuss.npuzzle.game.NPuzzle;
import com.repelliuss.npuzzle.ui.PuzzleLayoutManager;
import com.repelliuss.npuzzle.ui.SlidePuzzleSwipeListener;
import com.repelliuss.npuzzle.utils.Screen;

public class GameActivity extends AppCompatActivity
    implements View.OnTouchListener {

    private SlidePuzzleAdapter<Integer> adapter;
    private RecyclerView recyclerView;
    private Puzzle<SlidePuzzle<Integer>.Piece> puzzle;
    private GestureDetectorCompat detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {
            puzzle = new NPuzzle(extras.getInt(getString(R.string.key_row_count)),
                    extras.getInt(getString(R.string.key_column_count)));
        }
        else puzzle = new NPuzzle(3, 3);

        adapter = new SlidePuzzleAdapter<>(this, (NPuzzle) puzzle);

        activateSwipe();

        recyclerView = findViewById(R.id.rv_game_area);
        configurePuzzleView();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        return detector.onTouchEvent(motionEvent);
    }

    private void configurePuzzleView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new PuzzleLayoutManager(this, puzzle.getColumn()));

        recyclerView.addItemDecoration(new PuzzlePieceDecoration(puzzle.getColumn(),
                Screen.dpToPixel(-4, this)));

        recyclerView.setAdapter(adapter);
    }

    private void activateSwipe() {

        View swipeable = findViewById(R.id.v_swipeable);
        OnSwipeListener swipeListener =
                new SlidePuzzleSwipeListener<>((SlidePuzzle<Integer>) puzzle, adapter);
        detector = new GestureDetectorCompat(this, swipeListener);
        swipeable.setOnTouchListener(this);
    }
}
