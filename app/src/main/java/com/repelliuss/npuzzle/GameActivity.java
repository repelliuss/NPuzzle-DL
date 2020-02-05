package com.repelliuss.npuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.repelliuss.npuzzle.R;
import com.repelliuss.npuzzle.adapters.RecyclerViewAdapter;

public class GameActivity extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    private String[] data = { "1", "2", "3", "4", "5", "6", "7", "8"};
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3 ) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapter = new RecyclerViewAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }
}
