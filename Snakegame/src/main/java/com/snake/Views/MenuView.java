package com.snake.Views;

import com.snake.Settings;
import com.snake.Controllers.MenuController;
import com.snake.Templates.StyledButton;
import com.snake.Templates.StandardBackground;
import com.snake.Utils.Resources;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// Made by Kajsa Berlstedt & Lucia Little & Marinus Juhl

public class MenuView extends StandardBackground
{
    MenuController controller;

    public MenuView(MenuController controller)
    {
        this.controller = controller;

        initialize();
    }

    private void initialize()
    {

        // titl
        Text title = new Text("Snake!");
        title.setFill(Color.BLACK);
        Font crimes = Resources.getFontByName("Adventure", 70);
        title.setFont(crimes);
        setAlignment(title, Pos.TOP_CENTER);
        getChildren().add(title);
        title.setTranslateY(Settings.windowHeight * 0.1);

        // buttons
        StyledButton playButton = new StyledButton("play");
        StyledButton continueButton = new StyledButton("continue");
        StyledButton loadButton = new StyledButton("load");
        StyledButton quitButton = new StyledButton("quit", 10.5);

        playButton.button.setOnAction(controller::handlePlayButtonPressed);
        continueButton.button.setOnAction(controller::handleContinueButtonPressed);
        loadButton.button.setOnAction(controller::handleLoadButtonPressed);
        quitButton.button.setOnAction(controller::handleExitButtonPressed);


        VBox vbox = new VBox(0.05);
        vbox.getChildren().addAll(playButton, continueButton, loadButton);
        vbox.setStyle("-fx-alignment: center");
        vbox.setTranslateY(Settings.windowWidth * 0.05);
        getChildren().add(vbox);

        quitButton.setTranslateX(Settings.windowWidth * 0.4 - (quitButton.getPrefWidth() * 0.6));
        quitButton.setTranslateY(Settings.windowHeight * 0.4 - (quitButton.getPrefHeight() * 0.4));
        getChildren().add(quitButton);
    }

}
