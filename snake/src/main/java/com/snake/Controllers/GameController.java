package com.snake.Controllers;

import com.snake.Model.GameModel;
import com.snake.Model.Highscore;
import com.snake.Model.Vector;
import com.snake.Views.GameView;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class GameController implements IController
{
    private GameView view;
    private GameModel model;

    public Parent getView()
    {
        return view;
    }

    public GameController()
    {
        this.view = new GameView(500, 500);
        this.model = new GameModel();
    }

    public boolean executeNextStep()
    {
        model.nextState();
        if (model.gameOver()) {
            Highscore.setHighscore(model.getSnakeLength());
            return true;
        } else {
            view.update(model.getBoard());
            return false;
        }
    }

    public int getSpeed() {
        return model.getSpeed();
    }

    public void handleKeyPressed(KeyEvent key)
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

            default:
                break;
        }
    }
}
