package com.example.sudokuv4;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PuzzleView extends View {

    private static final String TAG = "Sudokuv4";

    private float width;
    private float height;
    private int indexX;
    private int indexY;
    public static final float CoefficientForBetterDisplay = 0.8f;


    private final Game game;

    public PuzzleView(Context context) {
        super(context);
        this.game = (Game) context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 9;
        height = h / 9;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        Paint largeSquares = new Paint();
        largeSquares.setColor(Color.RED);
        largeSquares.setStrokeWidth(7);

        Paint smallSquares = new Paint();
        smallSquares.setColor(Color.BLUE);
        smallSquares.setStrokeWidth(5);

        for (int i = 0; i < 9; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, smallSquares);
            canvas.drawLine(i * width, 0, i * width, getHeight(), smallSquares);
        }

        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                canvas.drawLine(0, i * height, getWidth(), i * height, largeSquares);
                canvas.drawLine(i * width, 0, i * width, getHeight(), largeSquares);
            }
        }

        Paint drawingNumbers = new Paint();
        drawingNumbers.setColor(Color.BLACK);
        drawingNumbers.setTextAlign(Paint.Align.CENTER);
        drawingNumbers.setTextSize(height * CoefficientForBetterDisplay);

        float x = width / 2;
        float y = height * CoefficientForBetterDisplay;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(this.game.getCellString(i, j), i * width + x, j * height + y, drawingNumbers);
            }
        }

        Paint mistake = new Paint();
        Rect rect = new Rect();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int movesleft = 9 - game.getUsedTiles(i, j).length;
                if (movesleft < 1) {
                    getRect(i, j, rect);
                    mistake.setColor(Color.RED);
                    canvas.drawRect(rect, mistake);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }
        indexX = (int) (event.getX() / width);
        indexY = (int) (event.getY() / height);
        game.showKeypadOrToast(indexX, indexY);
        Log.d(TAG, "x: " + indexX + ", y: " + indexY);
        return true;
    }


    public void setSelectedCell(int tile) {
        if (game.cellIfValid(indexX, indexY, tile)) {
            invalidate();
        }
    }

    public void getRect(int x, int y, Rect rect) {
        rect.set((int) (x * width), (int) (y * height), (int) (x * width + width), (int) (y * height + height));
    }

}

