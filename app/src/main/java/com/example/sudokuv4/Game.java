
package com.example.sudokuv4;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {

   public static final String KEY = "key";

   private static final String PREF_PUZZLE = "puzzle" ;
   
   public static final int FIRST_CARD = 0;
   public static final int SECOND_CARD = 1;
   public static final int THIRD_CARD = 2;
   
   protected static final int CONTINUE = -1;

   int[] puzzle = new int[81];

   private PuzzleView puzzleView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      int difficult = getIntent().getIntExtra(KEY, FIRST_CARD);
      puzzle = getPuzzle(difficult);
      calculationOfOccupiedCells();
      puzzleView = new PuzzleView(this);
      setContentView(puzzleView);
      getIntent().putExtra(KEY, CONTINUE);
   }

   @Override
   protected void onPause() {
      super.onPause();
      SharedPreferences sPref = getPreferences(MODE_PRIVATE);
      SharedPreferences.Editor ed = sPref.edit();
      ed.putString(PREF_PUZZLE, puzzlesInString(puzzle)).apply();
   }

   private int[] getPuzzle(int key) {
      String puzDif;
      String firstPuzzle = "530070000600195000098000060800060003400803001700020006060000280000419005000080079";
      String secondPuzzle = "100030008630802040007000500040608050500090006090503070004000200050201090900040007";
      String threePuzzle = "000060700059000000010200000000100000600500000300000460000000000000000091800740000";
      switch (key) {
         case CONTINUE:
            puzDif = getPreferences(MODE_PRIVATE).getString(PREF_PUZZLE, firstPuzzle);
            break;
         case THIRD_CARD:
            puzDif = threePuzzle;
            break;
         case SECOND_CARD:
            puzDif = secondPuzzle;
            break;
         case FIRST_CARD:
         default:
            puzDif = firstPuzzle;
            break;
      }
      return fromPuzzleString(puzDif);
   }

   static private String puzzlesInString(int[] puz) {
      StringBuilder buf = new StringBuilder();
      for (int element : puz) {
         buf.append(element);
      }
      return buf.toString();
   }
   static protected int[] fromPuzzleString(String string) {
      int[] cell = new int[string.length()];
      for (int i = 0; i < cell.length; i++) {
         char ch = string.charAt(i);
         int i1 = Character.getNumericValue(ch);
         cell[i] = i1;
      }
      return cell;
   }

   private int getCell(int x, int y) {
      return puzzle[y*9 + x];
   }

   private void setСell(int x, int y, int v) {
      puzzle[y*9 + x] = v;
   }

   protected String getCellString(int x, int y) {
      int s = getCell(x, y);
      if (s == 0)
         return "";
      else
         return String.valueOf(s);
   }

   protected boolean cellIfValid(int x, int y, int value) {
      int[] cells = getUsedTiles(x, y);
      if (value != 0) {
         for (int tile : cells) {
            if (tile == value)
               return false;
         }
      }
      setСell(x, y, value);
      calculationOfOccupiedCells();
      return true;
   }

   protected void showKeypadOrToast(int x, int y) {
      int[] tiles = getUsedTiles(x, y);
      if (tiles.length == 9) {
         Toast toast = Toast.makeText(this,
               R.string.no_action_label, Toast.LENGTH_SHORT);
         toast.setGravity(Gravity.CENTER, 0, 0);
         toast.show();
      } else {
         Dialog dialog = new Keypad(this, puzzleView);
         dialog.show();
      }
   }

   private final int[][][] usedTiles = new int[9][9][9];

   protected int[] getUsedTiles(int x, int y) {
      return usedTiles[x][y];
   }

   private void calculationOfOccupiedCells() {
      for (int x = 0; x < 9; x++) {
         for (int y = 0; y < 9; y++) {
            usedTiles[x][y] = calculationOfOccupiedCells(x, y);
         }
      }
   }

   private int[] calculationOfOccupiedCells(int x, int y) {
      int[] cell = new int[9];
      // horizontal
      for (int i = 0; i < 9; i++) {
         if (i == y)
            continue;
         int t = getCell(x, i);
         if (t != 0)
            cell[t - 1] = t;
      }
      // vertical
      for (int i = 0; i < 9; i++) {
         if (i == x)
            continue;
         int t = getCell(i, y);
         if (t != 0)
            cell[t - 1] = t;
      }
      // cell block
      int X = (x / 3) * 3;
      int Y = (y / 3) * 3;
      for (int i = X; i < X + 3; i++) {
         for (int j = Y; j < Y + 3; j++) {
            if (i == x && j == y)
               continue;
            int t = getCell(i, j);
            if (t != 0)
               cell[t - 1] = t;
         }
      }
      // remove 0
      int busyNumbers = 0;
      for (int t : cell) {
         if (t != 0)
            busyNumbers++;
      }
      int[] validNumbers = new int[busyNumbers];
      busyNumbers = 0;
      for (int t : cell) {
         if (t != 0)
            validNumbers[busyNumbers++] = t;
      }
      return validNumbers;
   }
}
