package com.snake.Controllers;

import com.snake.App;
import com.snake.Views.GUIView;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class GUIController implements IController {
    private GameController gameController;

    private GUIView view;

    private AnimationTimer gameTimer;
    private long lastUpdate;

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    private boolean isGameOver;
    private boolean isPaused;

    public GUIController() {
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
                if (frameTimeIndex == 0) {
                    arrayFilled = true;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                    view.updateFrameRate(frameRate);
                }
                if (now - lastUpdate >= 1_000_000_000 / gameController.getSpeed() && !isGameOver && !isPaused)
                {
                    isGameOver = gameController.executeNextStep();
                    lastUpdate = now;
                }
            }
        };
        gameTimer.start();
    }

    public Parent getView() {
        return view;
    }

    private void handleKeyPressed(KeyEvent key) {
        switch (key.getCode()) {
            case W:
            case S:
            case A:
            case D:
                gameController.handleKeyPressed(key);
                break;

            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                if (!isGameOver && !isPaused) {
                    gameController.handleKeyPressed(key);
                } else {
                    view.shiftFocus(key);
                }
                break;

            case ESCAPE:
                //gameTimer.stop();
                MenuController newController = new MenuController();
                App.setRoot(newController);
                break;

            default:
                break;
        }
    }
}
