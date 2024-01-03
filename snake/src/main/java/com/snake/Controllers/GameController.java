package com.snake.Controllers;

import com.snake.App;
import com.snake.Settings;
import com.snake.Model.GameModel;
import com.snake.Model.Vector;
import com.snake.Views.GameView;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class GameController implements IController
{
    private GameView view;
    private GameModel model;
    private AnimationTimer gameTimer;

    private long lastUpdate = 0;
    private boolean gameOver;

    public Parent getView()
    {
        return view;
    }

    public GameController(int rowCount, int columnCount)
    {
        this.view =
                new GameView(rowCount, columnCount, Settings.windowHeight, Settings.windowWidth);
        this.model = new GameModel(rowCount, columnCount);
        view.setOnKeyPressed(this::handleKeyPressed);
        Platform.runLater(() -> view.requestFocus());
        gameTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                if (now - lastUpdate >= 1_000_000_000 / model.getSpeed())
                {
                    timeLoop();
                    lastUpdate = now;
                }
            }
        };
        gameTimer.start();
    }

    private void timeLoop()
    {
        model.nextState();
        if (model.gameOver()) {
            gameTimer.stop();
            gameOver = true;
        } else {
            view.update(model.getBoard());
        }
    }

    private void handleKeyPressed(KeyEvent key)
    {
        switch (key.getCode())
        {
            case UP:
            case W:
                model.setDirection(new Vector(0, 1));
                break;

            case DOWN:
            case S:
                model.setDirection(new Vector(0, -1));
                break;

            case LEFT:
            case A:
                model.setDirection(new Vector(-1, 0));
                break;

            case RIGHT:
            case D:
                model.setDirection(new Vector(1, 0));
                break;

            case ESCAPE:
                gameTimer.stop();
                MenuController newController = new MenuController();
                App.setRoot(newController);
                break;

            default:
                System.out.println("non functional key " + key.getCode() + " pressed");
                break;
        }
    }
}
