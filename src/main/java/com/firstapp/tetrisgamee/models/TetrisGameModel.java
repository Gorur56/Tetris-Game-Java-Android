package com.firstapp.tetrisgamee.models;

import android.os.Handler;

import com.firstapp.tetrisgamee.presenters.GameModel;
import com.firstapp.tetrisgamee.presenters.GameTurn;
import com.firstapp.tetrisgamee.presenters.Point;
import com.firstapp.tetrisgamee.presenters.PointType;
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

    private int mScore;
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

        mUpcomingPoints = new Point[UPCOMING_AREA_SIZE][UPCOMING_AREA_SIZE];

        for (int i = 0; i < UPCOMING_AREA_SIZE; i++) {
            for (int j = 0; j < UPCOMING_AREA_SIZE; j++) {
                mUpcomingPoints[i][j] = mPoints[1+i][PLAYING_AREA_WIGHT+1+j];
            }
        }

        for (int i = 0; i < PLAYING_AREA_HEIGHT;i++)
        {
            mPoints[i][PLAYING_AREA_WIGHT].type = PointType.VERTICAL_LINE;
        }
        newGame();
    }

    @Override
    public int getGameSize() {
        return GAME_SIZE;
    }

    @Override
    public void newGame() {
        this.mScore = 0;

        for (int i = 0; i < PLAYING_AREA_HEIGHT; i++) {
            for (int j = 0; j < PLAYING_AREA_WIGHT; j++) {
                mPlayingPoints[i][j].type = PointType.EMPTY;
            }
        }
        mFallingPoints.clear();
    }

    private void generateUpcomingBrick()
    {
        BrickType upcomingBrick = BrickType.random();

        for (int i = 0; i < UPCOMING_AREA_SIZE; i++) {
            for (int j = 0; j < UPCOMING_AREA_SIZE; j++) {
                mUpcomingPoints[i][j].type = PointType.EMPTY;
            }
        }

        switch (upcomingBrick){
            case L:
                mUpcomingPoints[1][1].type = PointType.BOX;
                mUpcomingPoints[2][1].type = PointType.BOX;
                mUpcomingPoints[3][1].type = PointType.BOX;
                mUpcomingPoints[3][2].type = PointType.BOX;
                break;
            case T:
                mUpcomingPoints[1][1].type = PointType.BOX;
                mUpcomingPoints[2][1].type = PointType.BOX;
                mUpcomingPoints[3][1].type = PointType.BOX;
                mUpcomingPoints[2][2].type = PointType.BOX;
                break;
            case CHAIR:
                mUpcomingPoints[1][1].type = PointType.BOX;
                mUpcomingPoints[2][1].type = PointType.BOX;
                mUpcomingPoints[2][2].type = PointType.BOX;
                mUpcomingPoints[3][2].type = PointType.BOX;
                break;
            case STICK:
                mUpcomingPoints[0][1].type = PointType.BOX;
                mUpcomingPoints[1][1].type = PointType.BOX;
                mUpcomingPoints[2][1].type = PointType.BOX;
                mUpcomingPoints[3][1].type = PointType.BOX;
                break;
            case SQUARE:
                mUpcomingPoints[0][1].type = PointType.BOX;
                mUpcomingPoints[1][2].type = PointType.BOX;
                mUpcomingPoints[2][1].type = PointType.BOX;
                mUpcomingPoints[2][2].type = PointType.BOX;
                break;
        }
    }

    @Override
    public void startGame(PresenterObserver<Point[][]> onGameDrawnListener) {
        mIsGamePaused.set(false);
        final long sleepTime = 1000/FPs;
        new Thread(()->{
            long count = 0;
            while (!mIsGamePaused.get()){
                try {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                if( count % SPEED == 0)
                {
                    if(mIsTurning.get())
                    {
                        continue;
                    }
                    next();
                    mHandler.post(()->onGameDrawnListener.observe(mPlayingPoints));
                }
                count++;
            }
        }).start();

    }

    private synchronized void next()
    {

    }

    private void updateFallingPoints(){
        if(mFallingPoints.isEmpty()){
            for (int i = 0; i < UPCOMING_AREA_SIZE; i++) {
                for (int j = 0; j < UPCOMING_AREA_SIZE; j++) {
                    if(mUpcomingPoints[i][j].type == PointType.BOX)
                    {
                        mFallingPoints.add(new Point(j+3, i-4,
                                PointType.BOX,true));
                    }
                }

            }
            generateUpcomingBrick();
        }
    }

    private boolean isNextMerged(){
        for (Point fallingPoint:mFallingPoints) {
            if (fallingPoint.y + 1 >= 0 && (fallingPoint.y == PLAYING_AREA_HEIGHT -1 ||
                    getPlayingPoint(fallingPoint.x,fallingPoint.y).isStablePoint()))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isOutSide(){
        for (Point fallingPoint:mFallingPoints) {
            if(fallingPoint.y < 0){
                return true;
            }
        }
        return false;
    }

    private Point getPlayingPoint(int x, int y){
        if( x >= 0 && y >= 0 &&x < PLAYING_AREA_WIGHT && y < PLAYING_AREA_HEIGHT)
        {
            return mPlayingPoints[y][x];
        }
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
