
package com.example.sudokuv4;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class Keypad extends Dialog {

    private final View[] keys = new View[9];

    private final PuzzleView puzzle;

    Keypad(Context context, PuzzleView puzzle) {
        super(context);
        this.puzzle = puzzle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keypad);
        keys[0] = findViewById(R.id.keypad_1);
        keys[1] = findViewById(R.id.keypad_2);
        keys[2] = findViewById(R.id.keypad_3);
        keys[3] = findViewById(R.id.keypad_4);
        keys[4] = findViewById(R.id.keypad_5);
        keys[5] = findViewById(R.id.keypad_6);
        keys[6] = findViewById(R.id.keypad_7);
        keys[7] = findViewById(R.id.keypad_8);
        keys[8] = findViewById(R.id.keypad_9);
        for (int i = 0; i < keys.length; i++) {
            final int realNumber = i + 1;
            keys[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    puzzle.setSelectedCell(realNumber);
                    dismiss();
                }
            });
        }
    }
}
