package com.snake.Controllers;

import com.snake.Views.MenuView;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class MenuController implements IController {
    private MenuView view;

    public MenuController() {
        this.view = new MenuView();

        view.setOnKeyPressed(this::handleKeyPressed);
    }

    public Parent getView() {
        return view;
    }

    private void handleKeyPressed(KeyEvent key) {
        switch (key.getCode()) {
            case ESCAPE:
                Platform.exit();
                break;
        
            default:
                break;
        }
    }
}
