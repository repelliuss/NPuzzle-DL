package com.repelliuss.npuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.repelliuss.npuzzle.adapters.SlidePuzzleAdapter;
import com.repelliuss.npuzzle.game.Move;
import com.repelliuss.npuzzle.game.NPuzzle;
import com.repelliuss.npuzzle.managers.PuzzleLayoutManager;

public class GameActivity extends AppCompatActivity {

    private SlidePuzzleAdapter<Integer> adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        NPuzzle puzzle = new NPuzzle(9, 3);
        puzzle.move(Move.UP);
        puzzle.move(Move.LEFT);
        puzzle.reset();


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            int row = extras.getInt("ROW_NUMBER");
            int column = extras.getInt("COLUMN_NUMBER");


            TextView textView = findViewById(R.id.txt_game_size);
            StringBuilder gameSize = new StringBuilder(3);

            gameSize.append(row);
            gameSize.append('x');
            gameSize.append(column);

            textView.setText(gameSize);
        }

        recyclerView = findViewById(R.id.rv_game_area);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new PuzzleLayoutManager(this, 3 ));
        adapter = new SlidePuzzleAdapter<>(this, puzzle);
        recyclerView.setAdapter(adapter);
        puzzle.move(Move.LEFT);
    }
}
