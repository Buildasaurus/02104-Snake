package com.snake.Controllers;

import com.snake.Views.NewGameView;
import javafx.scene.Parent;

public class NewGameController implements IController
{
    NewGameView view;
    public NewGameController()
    {

    }

    public Parent getView()
    {
        return view;
    }
}
