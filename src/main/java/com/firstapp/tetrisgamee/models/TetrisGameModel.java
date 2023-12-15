package com.firstapp.tetrisgamee.models;

import android.os.Handler;

import com.firstapp.tetrisgamee.presenters.Point;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class TetrisGameModel {
    private static final int GAME_SIZE = 15;
    private static final int PLAYING_AREA_WIGHT = 10;
    private static final int PLAYING_AREA_HEIGHT = GAME_SIZE;
    private static final int UPCOMING_AREA_SIZE  = 4;

    private Point[][] mPoints;
    private Point[][] mPlayingPoints;
    private Point[][] mUpcomingPoints;

    private int score;
    private final AtomicBoolean mIsGamePaused = new AtomicBoolean();
    private final AtomicBoolean mIsTurning = new AtomicBoolean();
    private final LinkedList<Point> mFallingPoints = new LinkedList<>();

    private final Handler mHandler = new Handler();

    



}
