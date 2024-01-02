package com.snake;

import java.io.IOException;
import javax.swing.Icon;
import javafx.fxml.FXML;

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
