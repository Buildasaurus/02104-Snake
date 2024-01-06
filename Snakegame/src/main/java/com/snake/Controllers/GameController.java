package com.snake.Controllers;

import java.util.ArrayList;

import com.snake.Model.GameModel;
import com.snake.Model.GameState;
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

    public GameController(int width, int height)
    {
        this.view = new GameView(width, height);
        this.model = new GameModel();

        view.update(model.getBoard());
    }

    public GameController(int width, int height, GameState gameState) {
        this.view = new GameView(height, width);
        this.model = new GameModel(gameState);

        view.update(model.getBoard());
    }

    public boolean executeNextStep(ArrayList<Integer> updateList)
    {
        model.nextState(updateList);
        if (model.gameOver())
        {
            Highscore.setHighscore(model.getSnakeLength(0));
            Highscore.setHighscore(model.getSnakeLength(1));
            System.out.printf("There are %d players alive.\n", model.getAlivePlayerCount());
            return true;
        }
        else
        {
            view.update(model.getBoard());
            return false;
        }
    }

    public double getSpeed(int playerIndex)
    {
        return model.getSpeed(playerIndex);
    }

    public int getCurrentScore(int player)
    {
        return model.getSnakeLength(player);
    }

    public int getPlayerCount()
    {
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
                model.setDirection(new Vector(0, 1), 1);
                break;
            case DOWN:
                model.setDirection(new Vector(0, -1), 0);
                break;
            case S:
                model.setDirection(new Vector(0, -1), 1);
                break;
            case LEFT:
                model.setDirection(new Vector(-1, 0), 0);
                break;
            case A:
                model.setDirection(new Vector(-1, 0), 1);
                break;
            case RIGHT:
                model.setDirection(new Vector(1, 0), 0);
                break;
            case D:
                model.setDirection(new Vector(1, 0), 1);
                break;
            case J:
                model.setDirection(new Vector(1, 0), 2);
                break;
            case G:
                model.setDirection(new Vector(-1, 0), 2);
                break;
            case Y:
                model.setDirection(new Vector(0, 1), 2);
                break;
            case H:
                model.setDirection(new Vector(0, -1), 2);
                break;
            default:
                break;
        }
    }
}
