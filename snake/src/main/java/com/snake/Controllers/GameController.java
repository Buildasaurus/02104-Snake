package com.snake.Controllers;

import java.io.IOException;
import java.util.Timer;
import com.snake.App;
import com.snake.Model.GameModel;
import com.snake.Model.Vector;
import com.snake.Views.GameView;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class GameController implements IController
{
    GameView view;
    GameModel model;
    Timer gameTimer;
    Vector direction;

    public Parent getView()
    {
        // TODO
        return null;
    }

    public void GameController()
    {

    }

    void update(KeyEvent keyPressed)
    {

    }

    @FXML
    private void switchToSecondary() throws IOException
    {
        App.setRoot("secondary");
    }
}
