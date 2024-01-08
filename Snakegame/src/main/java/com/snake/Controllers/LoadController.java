package com.snake.Controllers;

import com.google.gson.Gson;
import com.snake.Views.LoadView;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

public class LoadController implements IController {
    LoadView view;

    public LoadController() {
        this.view = new LoadView(this);

        view.setOnKeyPressed(this::handleKeyPressed);
    }

    public Parent getView() {
        return view;
    }

    public void handleKeyPressed(KeyEvent key) {

    }

    public void handleLoadSaveButtonPressed(ActionEvent action) {
        Button actionOrigin = (Button) action.getSource();
        String saveName = actionOrigin.getText();

        //TODO JSON stuff here

        Gson gson = new Gson();
        
    }
}
