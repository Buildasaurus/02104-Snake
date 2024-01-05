package com.snake.Views;

import java.net.URL;

import com.snake.Controllers.MenuController;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
        URL url = getClass().getResource("/com/snake/Graphics/main.png");
        BackgroundImage BI = new BackgroundImage(new Image(url.toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.setBackground(new Background(BI));
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
    

    /* private void initialize() {
        // Creating buttons
        Button playButton = new Button("Play");
        Button exitButton = new Button("Quit");

        // Setting button fonts and styles
        playButton.setFont(new Font(30));
        exitButton.setFont(new Font(30));
        playButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
        exitButton.setStyle("-fx-background-color: orangered; -fx-text-fill: white;");

        // Setting action handlers from MenuController
        playButton.setOnAction(controller::handlePlayButtonPressed);
        exitButton.setOnAction(controller::handleExitButtonPressed);

        // Organizing buttons in a vertical layout
        VBox buttonBox = new VBox(playButton, exitButton);

        // Adding buttons to the StackPane
        getChildren().add(buttonBox);
        buttonBox.setAlignment(Pos.CENTER);
    }
     */
}

