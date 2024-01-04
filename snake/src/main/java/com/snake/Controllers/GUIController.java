package com.snake.Controllers;

import com.snake.App;
import com.snake.Views.GUIView;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class GUIController implements IController
{
    private GameController gameController;

    private GUIView view;

    private AnimationTimer gameTimer;
    private long lastUpdate;

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    private boolean isGameOver = false;
    private boolean isPaused = false;

    public GUIController()
    {
        this.gameController = new GameController();
        this.view = new GUIView(gameController.getView());

        view.setOnKeyPressed(this::handleKeyPressed);
        Platform.runLater(() -> view.requestFocus());

        gameTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                // DON'T TRUST THE FRAMERATE!!!
                long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0)
                {
                    arrayFilled = true;
                }
                if (arrayFilled)
                {
                    long elapsedNanos = now - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                    view.updateFrameRate(frameRate);
                }
                if (now - lastUpdate >= 1_000_000_000 / gameController.getSpeed() && !isGameOver
                        && !isPaused)
                {
                    isGameOver = gameController.executeNextStep();
                    view.updateCurrentScore(gameController.getCurrentScore(0));
                    lastUpdate = now;
                }
            }
        };
        gameTimer.start();
    }

    public Parent getView()
    {
        return view;
    }

    private void handleKeyPressed(KeyEvent key)
    {
        switch (key.getCode())
        {
            case W:
            case S:
            case A:
            case D:
            case LEFT:
            case RIGHT:
            case UP:
            case DOWN:
                if (!isGameOver && !isPaused)
                {
                    gameController.handleKeyPressed(key);
                }
                else
                {
                    view.shiftFocus(key);
                }
                break;

            case ESCAPE:
                if (isPaused)
                {
                    handleBackButtonPressed(null);
                }
                isPaused = true;
                view.setPauseView(this);
                break;

            default:
                break;
        }
    }

    public void handleResumeButtonPressed(ActionEvent action)
    {
        isPaused = false;
        view.removePauseView();
        Platform.runLater(() -> view.requestFocus());
    }

    public void handleSaveButtonPressed(ActionEvent action)
    {
        System.out.println("pressed save");
    }

    public void handleBackButtonPressed(ActionEvent action)
    {
        gameTimer.stop();
        MenuController newController = new MenuController();
        App.setRoot(newController);
    }
}
