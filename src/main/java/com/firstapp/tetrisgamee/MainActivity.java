 package com.firstapp.tetrisgamee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.firstapp.tetrisgamee.presenters.GamePresenter;
import com.firstapp.tetrisgamee.views.GameFrame;

 public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GameFrame gameFrame = findViewById(R.id.game_container);
        TextView gameScoreText = findViewById(R.id.game_score);
        TextView gameStatusText = findViewById(R.id.game_status);
        Button gameStlBtn = findViewById(R.id.game_ctl_btn);

        GamePresenter gamePresenter = new GamePresenter();
        gamePresenter.setGameModel();
    }
}