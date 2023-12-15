package com.firstapp.tetrisgamee.models;

import android.os.Handler;

import com.firstapp.tetrisgamee.presenters.GameModel;
import com.firstapp.tetrisgamee.presenters.GameTurn;
import com.firstapp.tetrisgamee.presenters.Point;
import com.firstapp.tetrisgamee.presenters.PresenterCompletableObserver;
import com.firstapp.tetrisgamee.presenters.PresenterObserver;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class TetrisGameModel implements GameModel {
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

    @Override
    public void init()
    {
        mPoints = new Point[GAME_SIZE][GAME_SIZE];
        for (int i = 0; i < GAME_SIZE; i++) {
            for (int j = 0; j < GAME_SIZE; j++) {
                mPoints[i][j] = new Point(j,i);
            }
        }
        mPlayingPoints = new Point[PLAYING_AREA_HEIGHT][PLAYING_AREA_WIGHT];
        for (int i = 0; i < PLAYING_AREA_HEIGHT; i++) {
            System.arraycopy(mPoints[i], 0, mPlayingPoints[i], 0, PLAYING_AREA_WIGHT);

        }

    }

    @Override
    public int getGameSize() {
        return 0;
    }

    @Override
    public void newGame() {

    }

    @Override
    public void startGame(PresenterObserver<Point[][]> onGameDrawnListener) {

    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void turn(GameTurn turn) {

    }

    @Override
    public void setGameOverListener(PresenterCompletableObserver onGameOverListener) {

    }

    @Override
    public void setScoreUpdateListener(PresenterObserver<Integer> onScoreUpdatedListener) {

    }

    private enum BrickType {
        L(0),T(1),CHAIR(2),STICK(3),SQUARE(4);
        final int mValue;

        BrickType(int value){
            this.mValue = value;
        }

        static  BrickType fromValue(int value){
            switch (value){
                case 1:
                    return T;
                case 2:
                    return CHAIR;
                case 3:
                    return STICK;
                case 4:
                    return SQUARE;
                case 0:
                default:
                    return L;
            }
        }

        static  BrickType random(){
            Random random = new Random();
            return fromValue(random.nextInt(5));
        }
    }





}
