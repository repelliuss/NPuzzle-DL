package com.repelliuss.npuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public final class MainActivity extends AppCompatActivity {

    private final Bundle boardSize = new Bundle(2);
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, GameActivity.class);
    }

    /**
     * Starts the game activity if user inputs are valid,
     * otherwise does nothing.
     * @param view Start button in activity_main.xml
     */
    public void startGame(View view) {

        if(view.getId() == R.id.btn_start) {
            if(getBoardSize()) {
                intent.putExtras(boardSize);
                startActivity(intent);
            }
        }
    }

    /**
     * Parses user input to integer and adds to bundle
     * boardSize if input is valid.
     * @return true if 2 < size < 10, false otherwise
     */
    private boolean getBoardSize() {

        EditText viewRow = findViewById(R.id.etxt_in_row);
        if(isEmpty(viewRow)) return false;

        EditText viewColumn = findViewById(R.id.etxt_in_column);
        if(isEmpty(viewColumn)) return false;

        int rowNumber = Integer.parseInt(viewRow.getText().toString().trim());
        if(rowNumber < 3 || rowNumber > 9) return false;

        int columnNumber = Integer.parseInt(viewColumn.getText().toString().trim());
        if(columnNumber < 3 || columnNumber > 9) return false;

        boardSize.putInt(getString(R.string.key_row_count), rowNumber);
        boardSize.putInt(getString(R.string.key_column_count), columnNumber);

        return true;
    }

    /**
     * Checks if EditText contains empty string
     * @param etxt String given from user
     * @return True if string is empty, false otherwise
     */
    private boolean isEmpty(EditText etxt) {
        return etxt.getText().toString().trim().length() == 0;
    }
}
