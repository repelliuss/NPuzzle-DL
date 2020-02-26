package com.repelliuss.npuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.repelliuss.npuzzle.game.Puzzle;
import com.repelliuss.npuzzle.game.SlidePuzzle;
import com.repelliuss.npuzzle.ui.OnSwipeListener;
import com.repelliuss.npuzzle.ui.PuzzlePieceDecoration;
import com.repelliuss.npuzzle.ui.SlidePuzzleAdapter;
import com.repelliuss.npuzzle.game.NPuzzle;
import com.repelliuss.npuzzle.ui.PuzzleLayoutManager;
import com.repelliuss.npuzzle.ui.SlidePuzzleSwipeListener;
import com.repelliuss.npuzzle.utils.AI;
import com.repelliuss.npuzzle.utils.Move;
import com.repelliuss.npuzzle.utils.PuzzleAI;
import com.repelliuss.npuzzle.utils.Screen;

public class GameActivity extends AppCompatActivity
    implements GameEventHandler, View.OnTouchListener{

    private SlidePuzzleAdapter<Integer> adapter;
    private RecyclerView recyclerView;
    private Puzzle<SlidePuzzle<Integer>.Piece> puzzle;
    private GestureDetectorCompat detector;
    private AI<Move> ai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final NPuzzle nPuzzle;
        TextView moveNum = findViewById(R.id.txt_move_num);

        if(extras != null) {
            nPuzzle = new NPuzzle(extras.getInt(getString(R.string.key_row_count)),
                    extras.getInt(getString(R.string.key_column_count)));
        }
        else nPuzzle = new NPuzzle(3, 3);

        puzzle = nPuzzle;
        adapter = new SlidePuzzleAdapter<>(this, nPuzzle, this, moveNum);
        recyclerView = findViewById(R.id.rv_game_area);
        ai = new PuzzleAI(GameActivity.this, puzzle);

        moveNum.setText(String.valueOf(puzzle.getMoveCount()));
        configurePuzzleView();
        activateSwipe();
        onGameStart();
    }

    @Override
    public void onGameStart() {
        puzzle.initialize();
    }

    @Override
    public void onGameFinish() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.info_puzzle_solved, null);
        View parent = findViewById(R.id.v_swipeable);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        return detector.onTouchEvent(motionEvent);
    }

    public void onNewGameClick(View view) {
        finish();
    }

    public void onHintClick(View view) {

        Move move = ai.predict();
        puzzle.move(move);
        adapter.notifyBlankMoved(move);
    }

    private void configurePuzzleView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new PuzzleLayoutManager(this, puzzle.getColumn()));

        recyclerView.addItemDecoration(new PuzzlePieceDecoration(puzzle.getColumn(),
                Screen.dpToPixel(0, getResources())));

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
