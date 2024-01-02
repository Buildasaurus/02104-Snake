package com.snake.Controllers;

import com.snake.Views.MenuView;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class MenuController implements IController {
    MenuView view;

    public MenuController(MenuView view) {
        this.view = view;
    }

    public Parent getView() {
        //TODO implement
        return null;
    }

    public void update(KeyEvent key) {

    }
}
