package com.repelliuss.npuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;

import com.repelliuss.npuzzle.ui.MenuAdapter;

public final class MainActivity extends AppCompatActivity {

    private Bundle boardSize;
    private MenuAdapter adapterRow;
    private MenuAdapter adapterColumn;
    private RecyclerView recyclerViewRow;
    private RecyclerView recyclerViewColumn;
    private SnapHelper snapHelperRow;
    private SnapHelper snapHelperColumn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardSize = new Bundle(2);
        intent = new Intent(this, GameActivity.class);

        adapterRow = new MenuAdapter(this);
        adapterColumn = new MenuAdapter(this);

        recyclerViewRow = findViewById(R.id.rv_row);
        recyclerViewColumn = findViewById(R.id.rv_column);

        snapHelperRow = new PagerSnapHelper();
        snapHelperColumn = new PagerSnapHelper();

        configureMenuView(recyclerViewRow, adapterRow, snapHelperRow);
        configureMenuView(recyclerViewColumn, adapterColumn, snapHelperColumn);
    }

    /**
     * Starts the game activity
     * @param view Start button in activity_main.xml
     */
    public void startGame(View view) {

        if(view.getId() == R.id.btn_start) {
            boardSize = getBoardSize();
            intent.putExtras(boardSize);
            startActivity(intent);
        }
    }

    /**
     * Parses user input to integer and adds to bundle
     */
    private Bundle getBoardSize() {

        View viewCenterRow = snapHelperRow.findSnapView(recyclerViewRow.getLayoutManager());
        int rowNumber = (recyclerViewRow.getChildAdapterPosition(viewCenterRow) % 7) + 3;

        View viewCenterColumn = snapHelperColumn.findSnapView(recyclerViewColumn.getLayoutManager());
        int columnNumber = (recyclerViewColumn.getChildAdapterPosition(viewCenterColumn) % 7) + 3;

        boardSize.putInt(getString(R.string.key_row_count), rowNumber);
        boardSize.putInt(getString(R.string.key_column_count), columnNumber);

        return boardSize;
    }

    private void configureMenuView(final RecyclerView recyclerView, final MenuAdapter adapter,
                                   final SnapHelper snapHelper) {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.getLayoutManager().scrollToPosition(Integer.MAX_VALUE / 2);
        snapHelper.attachToRecyclerView(recyclerView);
    }
}
