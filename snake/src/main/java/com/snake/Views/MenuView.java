package com.snake.Views;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MenuView extends StackPane{
    public MenuView() {
        initialize();
        Label label = new Label("hej");
        getChildren().add(label);
    }

    private void initialize() {

    }
}
