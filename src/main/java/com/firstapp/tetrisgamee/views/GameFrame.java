package com.firstapp.tetrisgamee.views;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.firstapp.tetrisgamee.presenters.Point;

public class GameFrame extends View {
    public GameFrame(Context context) {
        super(context);
    }

    public GameFrame(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GameFrame(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameFrame(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Point[][] mPoints;
    private  int mBoxSize;
    private  int mBoxPadding;
    private int mGameSize;

    private final Paint mPaint = new Paint();

    public void init(int gameSize)
    {
        this.mGameSize = gameSize;

        getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            mBoxSize = Math.min(getWidth(), getHeight())/mGameSize;
            mBoxPadding = mBoxSize / 10
        });
    }

    void setPoints(Point[][] points)
    {
        this.mPoints = points;
    }

    private Point getPoint(int x, int y)
    {
        return mPoints[y][x];
    }
}
