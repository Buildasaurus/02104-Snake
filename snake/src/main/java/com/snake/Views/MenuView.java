package com.snake.Views;

import com.snake.Controllers.MenuController;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MenuView extends StackPane
{
    MenuController controller;

    public MenuView(MenuController controller)
    {
        this.controller = controller;

        initialize();
    }

    private void initialize()
    {
        Button playButton = new Button("Play");
        Button exitButton = new Button("Quit");

        playButton.setFont(new Font(30));
        exitButton.setFont(new Font(30));

        playButton.setOnAction(controller::handlePlayButtonPressed);
        exitButton.setOnAction(controller::handleExitButtonPressed);

        VBox buttonBox = new VBox(playButton, exitButton);
        getChildren().add(buttonBox);
        buttonBox.setAlignment(Pos.CENTER);
    }
}
