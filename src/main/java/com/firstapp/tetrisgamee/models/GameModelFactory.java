package com.firstapp.tetrisgamee.models;

import com.firstapp.tetrisgamee.presenters.GameModel;

public class GameModelFactory {
    private GameModelFactory(){

    }

    public static GameModel newGameModel(GameType type)
    {
        switch (type){
            case TETRIS:
                return new TetrisGameModel();
            default:
                return null;
        }
    }
}
