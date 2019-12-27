
package com.example.sudokuv4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Sudoku extends AppCompatActivity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);
        Button newButton = findViewById(R.id.new_button);
        newButton.setOnClickListener(this);
        Button exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
        Button about = findViewById(R.id.about_button);
        about.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_button:
                Intent intent1 = new Intent(Sudoku.this, Game.class);
                intent1.putExtra(Game.KEY, Game.CONTINUE);
                startActivity(intent1);
                break;
            case R.id.new_button:
                openNewGameDialog();
                break;
            case R.id.about_button:
                Intent intent = new Intent(Sudoku.this, About.class);
                startActivity(intent);
                break;
            case R.id.exit_button:
                finish();
                break;
        }
    }

    private void openNewGameDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.new_game_title)
                .setItems(R.array.key, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        Intent intent = new Intent(Sudoku.this, Game.class);
                        intent.putExtra(Game.KEY, i);
                        startActivity(intent);
                    }
                }).show();
    }
}