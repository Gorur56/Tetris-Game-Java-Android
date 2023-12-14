package com.firstapp.tetrisgamee.presenters;

public interface GameModel {
    int FPD = 60;
    int SPEED = 25;
    void init();
    int getGameSize();
    void newGame();
    void startGame(PresenterObserver<Point[][]> onGameDrawnListener);

    void pauseGame();
    void turn(GameTurn turn);
    void setGameOverListener(PresenterCompletableObserver onGameOverListener);
    void setScoreUpdateListener(PresenterObserver<Integer> onScoreUpdatedListener);
}
