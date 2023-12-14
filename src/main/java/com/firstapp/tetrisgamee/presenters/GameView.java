package com.firstapp.tetrisgamee.presenters;

public interface GameView {
    void init(int gameSize);
    void draw(Point[][] points);
    void setScore(int status);
    void setStatus(GameStatus status);
}
