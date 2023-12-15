package com.firstapp.tetrisgamee.views;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.firstapp.tetrisgamee.presenters.GameStatus;
import com.firstapp.tetrisgamee.presenters.GameView;
import com.firstapp.tetrisgamee.presenters.Point;

public class GameViewImpl implements GameView {
    private final GameFrame mGameFrame;
    private final TextView mGameScoreText;
    private final TextView mGameStatusText;
    private final Button mGameCtlBtn;

    GameViewImpl(GameFrame gameFrame, TextView gameScoreText, TextView gameStatusText,
                 Button gameCtlBtn)
    {
        this.mGameFrame = gameFrame;
        this.mGameScoreText = gameScoreText;
        this.mGameStatusText = gameStatusText;
        this.mGameCtlBtn = gameCtlBtn;
    }

    @Override
    public void init(int gameSize)
    {
        mGameFrame.init(gameSize);
    }

    @Override
    public void draw(Point[][] points)
    {
        mGameFrame.setPoints(points);
        mGameFrame.invalidate();
    }

    @Override
    public void setScore(int score)
    {
        mGameScoreText.setText("Score:" + score);
    }

    @Override
    public void setStatus(GameStatus status)
    {
        mGameStatusText.setText(status.getValue());
        mGameStatusText.setVisibility(status == GameStatus.PLAYING ? View.VISIBLE: View.INVISIBLE);
        mGameCtlBtn.setText(status == GameStatus.PLAYING? "Pause":"Start");

    }
}
