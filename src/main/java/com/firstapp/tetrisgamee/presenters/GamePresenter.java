package com.firstapp.tetrisgamee.presenters;

public class GamePresenter {
    private GameView mGameView;
    private  GameModel mGameModel;
    private GameStatus mStatus;

    public void setGameView(GameView gameView)
    {
        this.mGameView = gameView;
    }

    public void setGameModel(GameModel mGameModel)
    {
        this.mGameModel = mGameModel;
    }

    public void init(){
        mGameModel.init();
        mGameView.init(mGameModel.getGameSize());
        mGameModel.setGameOverListener(()->setStatus(GameStatus.OVER));
        mGameModel.setScoreUpdateListener(mGameView::setScore);
        setStatus(GameStatus.START);
    }

    public  void turn(GameTurn turn)
    {
        mGameModel.turn(turn);
    }

    public void changeStatus(){
        if( mStatus == GameStatus.PLAYING)
        {
            pauseGame();
        }
        else
        {
            startGame();
        }
    }

    private void pauseGame()
    {
        setStatus(GameStatus.PAUSED);
        mGameModel.pauseGame();
    }

    private void startGame(){
        setStatus(GameStatus.PLAYING);
        mGameModel.startGame(mGameView::draw);
    }

    private void setStatus(GameStatus status)
    {
        if( mStatus == GameStatus.OVER  || status == GameStatus.START)
        {
            mGameModel.newGame();
        }

        mStatus = status;
        mGameView.setStatus(status);
    }
}

