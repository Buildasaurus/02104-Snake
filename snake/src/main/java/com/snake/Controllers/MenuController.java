package com.snake.Controllers;

import com.snake.Views.MenuView;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class MenuController implements IController {
    MenuView view;

    public MenuController() {
        this.view = new MenuView();
    }

    public Parent getView() {
        return view;
        //TODO implement
    }

    public void update(KeyEvent key) {

    }
}
