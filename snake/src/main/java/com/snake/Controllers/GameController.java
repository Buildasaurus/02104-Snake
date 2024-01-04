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

    private boolean is2Player;

    public Parent getView()
    {
        return view;
    }

    public GameController()
    {
        this.view = new GameView(500, 500);
        this.model = new GameModel();
        if (getPlayerCount() == 2) {
            is2Player = true;
        } else {
            is2Player = false;
        }
    }

    public boolean executeNextStep()
    {
        model.nextState();
        if (model.gameOver())
        {
            Highscore.setHighscore(model.getSnakeLength(0));
            Highscore.setHighscore(model.getSnakeLength(1));
            return true;
        }
        else
        {
            view.update(model.getBoard());
            return false;
        }
    }

    public double getSpeed()
    {
        return model.getSpeed();
    }

    public int getCurrentScore(int player) {
        return model.getSnakeLength(player);
    }

    public int getPlayerCount() {
        return model.getPlayerCount();
    }

    public void handleKeyPressed(KeyEvent key)
    {
        switch (key.getCode())
        {
            case UP:
                model.setDirection(new Vector(0, 1), 0);
                break;
            case W:
                if (is2Player) {
                    model.setDirection(new Vector(0, 1), 1);
                } else {
                    model.setDirection(new Vector(0, 1), 0);
                }
                break;

            case DOWN:
                model.setDirection(new Vector(0, -1), 0);
                break;
            case S:
                if (is2Player) {
                    model.setDirection(new Vector(0, -1), 1);
                } else {
                    model.setDirection(new Vector(0, -1), 0);
                }
                break;

            case LEFT:
                model.setDirection(new Vector(-1, 0), 0);
                break;
            case A:
                if (is2Player) {
                    model.setDirection(new Vector(-1, 0), 1);
                } else {
                    model.setDirection(new Vector(-1, 0), 0);
                }
                break;

            case RIGHT:
                model.setDirection(new Vector(1, 0), 0);
                break;
            case D:
                if (is2Player) {
                    model.setDirection(new Vector(1, 0), 1);
                } else {
                    model.setDirection(new Vector(1, 0), 0);
                }
                break;

            default:
                break;
        }
    }
}
