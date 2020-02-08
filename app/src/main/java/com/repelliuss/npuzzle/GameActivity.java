package com.repelliuss.npuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.repelliuss.npuzzle.ui.PuzzlePieceDecoration;
import com.repelliuss.npuzzle.ui.SlidePuzzleAdapter;
import com.repelliuss.npuzzle.game.NPuzzle;
import com.repelliuss.npuzzle.ui.PuzzleLayoutManager;
import com.repelliuss.npuzzle.utils.Screen;

public class GameActivity extends AppCompatActivity {

    private SlidePuzzleAdapter<Integer> adapter;
    private RecyclerView recyclerView;
    private NPuzzle puzzle;

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

        adapter = new SlidePuzzleAdapter<>(this, puzzle);

        recyclerView = findViewById(R.id.rv_game_area);
        configurePuzzleView();

    }

    private void configurePuzzleView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new PuzzleLayoutManager(this, puzzle.getColumn()));

        recyclerView.addItemDecoration(new PuzzlePieceDecoration(puzzle.getColumn(),
                Screen.dpToPixel(-4, this)));

        recyclerView.setAdapter(adapter);
    }
}
