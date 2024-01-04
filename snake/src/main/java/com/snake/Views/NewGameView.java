package com.snake.Views;

import com.snake.Controllers.NewGameController;
import javafx.scene.layout.GridPane;

public class NewGameView extends GridPane {
    NewGameController controller;
    public NewGameView(NewGameController controller)
    {
        this.controller = controller;
    }
}
