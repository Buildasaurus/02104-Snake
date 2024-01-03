package com.snake.Controllers;

import java.util.Timer;
import java.util.TimerTask;

import com.snake.App;
import com.snake.Settings;
import com.snake.Model.GameModel;
import com.snake.Model.Vector;
import com.snake.Views.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GameController implements IController
{
    private GameView view;
    private GameModel model;
    private AnimationTimer gameTimer;

    public Parent getView()
    {
        return view;
    }

    private long lastUpdate = 0;

    public GameController(int rowCount, int columnCount)
    {
        this.view =
                new GameView(rowCount, columnCount, Settings.windowHeight, Settings.windowWidth);
        this.model = new GameModel(rowCount, columnCount);

        view.setOnKeyPressed(this::handleKeyPressed);

        gameTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                if (now - lastUpdate >= 1_000_000_000)
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
        view.update(model.getBoard());
    }

    void handleKeyPressed(KeyEvent key)
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
                MenuController newController = new MenuController();
                App.setRoot(newController);
                break;

            default:
                System.out.println("non functional key " + key.getCode() + " pressed");
                break;
        }
    }
}
