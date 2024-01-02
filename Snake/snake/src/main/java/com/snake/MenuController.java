package com.snake;

import javafx.scene.input.KeyEvent;

public class MenuController implements IController {
    MenuView view;

    public MenuController(MenuView view) {
        this.view = view;
    }

    public int getView() {
        return 1;
    }

    public void update(KeyEvent key) {

    }
}
