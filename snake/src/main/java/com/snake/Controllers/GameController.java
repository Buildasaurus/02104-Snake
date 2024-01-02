package com.snake.Controllers;

import java.util.Timer;
import java.util.TimerTask;

import com.snake.Settings;
import com.snake.Model.GameModel;
import com.snake.Model.Vector;
import com.snake.Views.GameView;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class GameController implements IController
{
    GameView view;
    GameModel model;
    Timer gameTimer;

    int speed;

    public Parent getView()
    {
        return view;
    }

    public GameController(int rowCount, int columnCount)
    {
        this.view = new GameView(rowCount, columnCount, Settings.windowHeight, Settings.windowWidth);
        this.model = new GameModel(rowCount, columnCount);
        this.gameTimer = new Timer();
        this.speed = 2;

        view.setOnKeyPressed(this::handleKeyPressed);

        gameTimer.schedule(timeLoop(), 1000/speed);
    }

    private TimerTask timeLoop() {
        // this automatically handles changes in speed by finishing the current one and applying after that, how sexy
        TimerTask task = new TimerTask() {
            public void run() {
                model.nextState();
                gameTimer.schedule(timeLoop(), 1000/speed);
            }
        };

        return task;
    }

    void handleKeyPressed(KeyEvent key) {
        switch (key.getCode()) {
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
                Platform.exit();

            default:
                System.out.println("non functional key " + key.getCode() + " pressed");
                break;
        }
    }
}
