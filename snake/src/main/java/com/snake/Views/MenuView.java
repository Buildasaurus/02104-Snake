package com.snake.Views;

import com.snake.Controllers.MenuController;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuView extends StackPane {
    MenuController controller;

    public MenuView(MenuController controller) {
        this.controller = controller;

        initialize();
    }

    private void initialize() {
        Button playButton = new Button("Play");
        Button exitButton = new Button("Quit");

        playButton.setOnAction(controller::handlePlayButtonPressed);
        exitButton.setOnAction(controller::handleExitButtonPressed);

        VBox buttonBox = new VBox(playButton, exitButton);
        getChildren().add(buttonBox);
    }
}
